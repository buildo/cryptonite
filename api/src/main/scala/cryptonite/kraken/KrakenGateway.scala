package cryptonite.kraken

import scala.concurrent.{Future, ExecutionContext}

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import com.softwaremill.sttp.akkahttp._
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.semiauto.deriveDecoder
import io.buildo.enumero.circe._
import io.circe.syntax._
import cats.instances.future._
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
      supportedProducts.zip(krakenTickers).map { case (p, t) => convertTicker(p, t) }
    }).leftMap{x => ApiError.KrakenError}.value

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
    val response = request.response(asJson[KrakenResult[KrakenTicker]]).send()
    response.map(x => collapseEithers(x.body).map(_.result))
  }

  private def products(): Future[Either[String, List[KrakenProduct]]] = {
    val request = sttp.get(uri"https://api.kraken.com/0/public/AssetPairs")
    val response = request.response(asJson[KrakenResult[KrakenProduct]]).send()
    response.map(x => collapseEithers(x.body).map(_.result))
  }

  private def collapseEithers[A,B](e : Either[String, Either[A, B]]): Either[String, B] = {
    e.map(_.left.map(_.toString)).joinRight
  }

  private def objToArray(json: Json): Json = {
    json.withObject { obj =>
      val arr = obj.toMap.map { case (k, v) => v.mapObject(_.add("id", k.asJson)) }
      Json.fromValues(arr)
    }
  }

  private def arrayToFirst(json: Json): Json = {
    json.withArray(_.headOption.getOrElse(Json.Null))
  }

  implicit def krakenResultDecoder[A: Decoder]: Decoder[KrakenResult[A]] =
    deriveDecoder[KrakenResult[A]]
      .prepare(_.downField("result").withFocus(objToArray).up)

  implicit val tickerDecoder: Decoder[KrakenTicker] =
    deriveDecoder[KrakenTicker]
      .prepare(_.downField("a").withFocus(arrayToFirst).up.downField("b").withFocus(arrayToFirst).up)
}
