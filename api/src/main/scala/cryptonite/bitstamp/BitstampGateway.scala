package cryptonite.bitstamp

import scala.concurrent.{Future, ExecutionContext}

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import com.softwaremill.sttp.akkahttp._
import io.circe.generic.auto._
import io.buildo.enumero.circe._
import cats.instances.future._
import cats.instances.list._
import cats.syntax.traverse._
import cats.data.EitherT

import cryptonite.model._
import cryptonite.model.exchange._
import cryptonite.bitstamp.model._
import cryptonite.errors.ApiError


class BitstampGateway(implicit ec: ExecutionContext) {

  implicit val sttpBackend = AkkaHttpBackend()

  def read(): Future[Either[ApiError, List[Ticker]]] = {
    (for {
      products <- EitherT(products())
      supportedProducts <- EitherT.pure[Future, String, List[SupportedProduct]](products.flatMap(supportedProduct))
      bitstampTickers <- supportedProducts.traverse(x => EitherT(ticker(x.id)))
    } yield {
      supportedProducts.zip(bitstampTickers).map { case (p, t) => convertTicker(p, t) }
    }).leftMap{x => ApiError.BitstampError}.value

  }

  private def supportedProduct(p: BitstampProduct): Option[SupportedProduct] = {
    val currencies = p.name.split("/")
    (BitstampCurrencies.convertCurrency(currencies.head), BitstampCurrencies.convertCurrency(currencies.last)) match {
      case (Some(baseCurrency), Some(quoteCurrency)) if baseCurrency.index.isCrypto => Some(SupportedProduct(
        id = p.url_symbol,
          product = Product(
          base = baseCurrency,
          quote = quoteCurrency
        )
      ))
      case _ => None
    }
  }

  private def convertTicker(p: SupportedProduct, t: BitstampTicker): Ticker = Ticker(
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

  private def ticker(id: String): Future[Either[String, BitstampTicker]] = {
    val request = sttp.get(uri"https://www.bitstamp.net/api/v2/ticker/$id")
    val response = request.response(asJson[BitstampTicker]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def products(): Future[Either[String, List[BitstampProduct]]] = {
    val request = sttp.get(uri"https://www.bitstamp.net/api/v2/trading-pairs-info/")
    val response = request.response(asJson[List[BitstampProduct]]).send()
    response.map(x => collapseEithers(x.body))
  }

  private def collapseEithers[A,B](e : Either[String, Either[A, B]]): Either[String, B] = {
    e.map(_.left.map(_.toString)).joinRight
  }
}
