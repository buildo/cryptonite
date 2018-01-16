package cryptonite.bitfinex

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
import cryptonite.bitfinex.model._
import cryptonite.errors.ApiError

class BitfinexGateway(implicit ec: ExecutionContext) {

  implicit val sttpBackend = AkkaHttpBackend()

  def read(): Future[Either[ApiError, List[Ticker]]] = {
    (for {
      products <- EitherT(products())
      supportedProducts <- EitherT.pure[Future, String, List[SupportedProduct]](products.flatMap(supportedProduct))
      tickers <- supportedProducts.traverse(x => EitherT(ticker(x.id)))
    } yield {
      supportedProducts.zip(tickers).map{case (p,t) => convertTicker(p,t)}
    }).leftMap{x => ApiError.GenericError}.value

  }

  private def supportedProduct(s: String): Option[SupportedProduct] = {
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

  private def convertTicker(p: SupportedProduct, t: BitfinexTicker): Ticker = Ticker(
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

  private def tickers(products: List[SupportedProduct]): Future[Either[String, List[BitfinexTicker]]] = {
    val symbols = products.map(_.id).mkString(",")
    val request = sttp.get(uri"https://api.bitfinex.com/v2/tickers?symbols=$symbols")
    val response = request.response(asJson[List[BitfinexTicker]]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def products(): Future[Either[String, List[String]]] = {
    val request = sttp.get(uri"https://api.bitfinex.com/v1/symbols")
    val response = request.response(asJson[List[String]]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def collapseEithers[A,B](e : Either[String, Either[A, B]]): Either[String, B] = {
    e.map(_.left.map(_.toString)).joinRight
  }
}
