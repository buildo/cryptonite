package cryptonite.kraken

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
import cryptonite.kraken.model._
import cryptonite.errors.ApiError


class KrakenGateway(implicit ec: ExecutionContext) {

  implicit val sttpBackend = AkkaHttpBackend()

  def read(): Future[Either[ApiError, List[Ticker]]] = {
    (for {
      products <- EitherT(products())
      supportedProducts <- EitherT.pure[Future, String, List[SupportedProduct]](products.flatMap(supportedProduct))
      krakenTickers <- EitherT(tickers(supportedProducts))
    } yield {
      supportedProducts.zip(krakenTickers).map{case (p,t) => convertTicker(p,t)}
    }).leftMap{x => ApiError.GenericError}.value

  }

  private def supportedProduct(p: KrakenProduct): Option[SupportedProduct] = {
    (KrakenCurrencies.convertCurrency(p.base), KrakenCurrencies.convertCurrency(p.quote)) match {
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

  private def convertTicker(p: SupportedProduct, t: KrakenTicker): Ticker = Ticker(
    product = p.product,
    bid = Amount(
      value = t.b,
      currency = p.product.quote
    ),
    ask = Amount(
      value = t.a,
      currency = p.product.quote
    )
  )

  private def tickers(products: List[SupportedProduct]): Future[Either[String, List[KrakenTicker]]] = {
    val productIds = products.map(_.id).mkString(",")
    val request = sttp.get(uri"https://api.kraken.com/0/public/Ticker?pair=$productIds")
    val response = request.response(asJson[List[KrakenTicker]]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def products(): Future[Either[String, List[KrakenProduct]]] = {
    val request = sttp.get(uri"https://api.kraken.com/0/public/AssetPairs")
    val response = request.response(asJson[List[KrakenProduct]]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def collapseEithers[A,B](e : Either[String, Either[A, B]]): Either[String, B] = {
    e.map(_.left.map(_.toString)).joinRight
  }
}
