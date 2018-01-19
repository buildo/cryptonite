package cryptonite.gdax

import java.util.concurrent.Executors
import scala.concurrent.{Future, ExecutionContext}

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import com.softwaremill.sttp.akkahttp._
import io.circe.generic.auto._
import cats.syntax.traverse._
import cats.instances.future._
import cats.instances.list._
import cats.data.EitherT

import cryptonite.model._
import cryptonite.model.exchange._
import cryptonite.gdax.model._
import cryptonite.errors.ApiError

class GDAXGateway() extends Gateway {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  implicit val sttpBackend = AkkaHttpBackend()

  override def read(): Future[Either[ApiError, List[Ticker]]] = {
    (for {
      products <- EitherT(products())
      supportedProducts <- EitherT.pure[Future, String, List[SupportedProduct]](products.flatMap(supportedProduct))
      gdaxTickers <- supportedProducts.traverse(x => EitherT(ticker(x.id)))
    } yield {
      supportedProducts.zip(gdaxTickers).map{case (p,t) => convertTicker(p,t)}
    }).leftMap{x => ApiError.GDAXError}.value
  }

  override def exchange(): Exchange = Exchange.GDAX

  private def supportedProduct(p: GDAXProduct): Option[SupportedProduct] = {
    (Currencies.currencyFromString(p.base_currency), Currencies.currencyFromString(p.quote_currency)) match {
      case (Some(baseCurrency), Some(quoteCurrency)) => Some(SupportedProduct(
        id = p.id,
        product = Product(
          base = baseCurrency,
          quote = quoteCurrency
        )
      ))
      case _ => None
    }
  }

  private def convertTicker(p: SupportedProduct, t: GDAXTicker): Ticker = Ticker(
    product = p.product,
    bid = Amount(
      value = t.bid,
      currency = p.product.quote
    ),
    ask = Amount(
      value = t.ask,
      currency = p.product.quote
    )
  )

  private def ticker(id: String): Future[Either[String, GDAXTicker]] = {
    Thread.sleep(500)
    val request = sttp.get(uri"https://api.gdax.com/products/$id/ticker")
    val response = request.response(asJson[GDAXTicker]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def products(): Future[Either[String, List[GDAXProduct]]] = {
    val request = sttp.get(uri"https://api.gdax.com/products/")
    val response = request.response(asJson[List[GDAXProduct]]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def collapseEithers[A,B](e : Either[String, Either[A, B]]): Either[String, B] = {
    e.map(_.left.map(_.toString)).joinRight
  }
}
