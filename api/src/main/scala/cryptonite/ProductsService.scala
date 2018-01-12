package cryptonite

import scala.concurrent.{Future, ExecutionContext}

import cats.syntax.traverse._
import cats.instances.future._
import cats.instances.list._
import cats.data.EitherT

import cryptonite.model._
import cryptonite.gdax.model.Ticker
import cryptonite.errors.ApiError
import cryptonite.gdax.GDAXGateway

class ProductsService(gateway: GDAXGateway, portfolioService: PortfolioService)(implicit ec: ExecutionContext) {

  def read(): Future[Either[ApiError, List[Book]]] = {
    (for {
      tickers <- EitherT(gateway.read())
      amounts <- tickers.traverse(x => EitherT(portfolioService.get(x.product.base.index.code)))
    } yield {
      tickers.zip(amounts).map{case (t,a) => createBook(t,a)}
    }).value
  }

  private def createBook(t: Ticker, a: Amount): Book =
    Book(
      exchange = Exchange.GDAX,
      product = t.product,
      bid = t.bid,
      ask = t.ask,
      value = Amount (
        value = a.value * t.bid.value,
        currency = t.product.quote
      )
    )
}