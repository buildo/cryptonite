package cryptonite

import scala.concurrent.{Future, ExecutionContext}

import cats.syntax.traverse._
import cats.instances.future._
import cats.instances.list._
import cats.data.EitherT

import cryptonite.model._
import cryptonite.model.exchange.Ticker
import cryptonite.errors.ApiError
import cryptonite.gdax.GDAXGateway
import cryptonite.kraken.KrakenGateway

class ProductsService(gdaxGateway: GDAXGateway, krakenGateway: KrakenGateway, portfolioService: PortfolioService)(implicit ec: ExecutionContext) {

  def read(): Future[Either[ApiError, List[Book]]] = {
    (for {
      gdaxTickers <- EitherT(gdaxGateway.read())
      krakenTickers <- EitherT(krakenGateway.read())
      gdaxAmounts <- gdaxTickers.traverse(x => EitherT(portfolioService.get(x.product.base.index.code)))
      krakenAmounts <- krakenTickers.traverse(x => EitherT(portfolioService.get(x.product.base.index.code)))
    } yield {
      val gdaxBooks = gdaxTickers.zip(gdaxAmounts).map{case (t,a) => createBook(Exchange.GDAX,t,a)}
      val krakenBooks = krakenTickers.zip(krakenAmounts).map{case (t,a) => createBook(Exchange.Kraken,t,a)}
      List.concat(gdaxBooks,krakenBooks)
    }).value
  }

  private def createBook(e: Exchange, t: Ticker, a: Amount): Book =
    Book(
      exchange = e,
      product = t.product,
      bid = t.bid,
      ask = t.ask,
      value = Amount (
        value = a.value * t.bid.value,
        currency = t.product.quote
      )
    )
}
