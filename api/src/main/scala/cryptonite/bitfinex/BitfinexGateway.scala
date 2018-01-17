package cryptonite.bitfinex

import scala.concurrent.{Future, ExecutionContext}

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import com.softwaremill.sttp.akkahttp._
import io.circe._
import io.circe.generic.semiauto.deriveDecoder
import cats.instances.future._
import cats.data.EitherT
import io.buildo.enumero.circe._

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
      bitfinexTickers <- EitherT(tickers(supportedProducts))
    } yield {
      supportedProducts.zip(bitfinexTickers).map { case (p, t) => convertTicker(p, t) }
    }).leftMap{x => ApiError.GenericError}.value

  }

  private def supportedProduct(s: String): Option[SupportedProduct] = {
    val (base, quote) = s.toUpperCase.splitAt(3)
    (BitfinexCurrencies.convertCurrency(base), BitfinexCurrencies.convertCurrency(quote)) match {
      case (Some(baseCurrency), Some(quoteCurrency)) => Some(SupportedProduct(
        id = "t"+s,
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

  private val tickerResultFields = List("symbol", "bid", "bidSize", "ask", "askSize")

  implicit val tickerDecoder: Decoder[BitfinexTicker] =
    deriveDecoder[BitfinexTicker]
      .prepare(_.withFocus(_.withArray(arr => Json.obj(tickerResultFields.zip(arr): _*))))

}
