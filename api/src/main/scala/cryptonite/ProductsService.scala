package cryptonite

import scala.concurrent.{Future, ExecutionContext}

import cats.syntax.traverse._
import cats.instances.future._
import cats.instances.list._
import cats.data.EitherT

import cryptonite.model._
import cryptonite.model.exchange.Ticker
import cryptonite.errors.ApiError

class ProductsService(gateways: List[Gateway], portfolioService: PortfolioService)(implicit ec: ExecutionContext) {

  def read(
    exchanges: List[Exchange],
    base: Option[Currency],
    quote: Option[Currency]
  ): Future[Either[ApiError, List[Book]]] = {

    val filteredGateways = gateways.filter(g => exchanges.contains(g.exchange()))
    (for {
      booksByGateway <- filteredGateways.traverse(g => EitherT(getBooks(g)))
    } yield {
      val books = booksByGateway.flatten
      books.filter(b => acceptBase(b,base)).filter(b => acceptQuote(b,quote))
    }).value

  }

  def getBooks(gateway: Gateway): Future[Either[ApiError, List[Book]]] = {
    (for {
      tickers <- EitherT(gateway.read())
      amounts <- tickers.traverse(t => EitherT(portfolioService.get(t.product.base.index.code)))
    } yield {
      val books = tickers.zip(amounts).map { case (t, a) => createBook(gateway.exchange(), t, a) }
      books
    }).value
  }

  def acceptBase(book: Book, base: Option[Currency]): Boolean = {
    base match {
      case Some(currency) => if (book.product.base == currency) true else false
      case None => true
    }
  }

  def acceptQuote(book: Book, quote: Option[Currency]): Boolean = {
    quote match {
      case Some(currency) => if (book.product.quote == currency) true else false
      case None => true
    }
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
