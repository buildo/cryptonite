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
    quote: Option[Currency],
    sortBy: Column,
    ascending: Boolean
  ): Future[Either[ApiError, List[Book]]] = {

    val filteredGateways = gateways.filter(g => exchanges.contains(g.exchange()))
    (for {
      booksByGateway <- filteredGateways.traverse(g => EitherT(getBooks(g)))
    } yield {
      val books = booksByGateway.flatten
      val filteredBooks = books
        .filter(b => acceptBase(b,base))
        .filter(b => acceptQuote(b,quote))
      val sortedBooks = sortBooks(filteredBooks, sortBy)
      order(sortedBooks, ascending)
    }).value

  }

  def getBooks(gateway: Gateway): Future[Either[ApiError, List[Book]]] =
    (for {
      tickers <- EitherT(gateway.read())
      amounts <- tickers.traverse(t => EitherT(portfolioService.get(t.product.base.index.code)))
    } yield {
      tickers.zip(amounts).map { case (t, a) => createBook(gateway.exchange(), t, a) }
    }).value

  def acceptBase(book: Book, base: Option[Currency]): Boolean =
    base.forall{ b => if (book.product.base == b) true else false }

  def acceptQuote(book: Book, quote: Option[Currency]): Boolean =
    quote.forall{ q => if (book.product.quote == q) true else false }

  def sortBooks(books: List[Book], sortBy: Column): List[Book] =
    sortBy match {
      case Column.Exchange => books.sortBy(x => Exchange.caseToString(x.exchange))
      case Column.Product => books.sortBy(_.product.base.index.code)
      case Column.Bid => books.sortBy(_.bid.value)
      case Column.Ask => books.sortBy(_.ask.value)
      case Column.Value => books.sortBy(_.value.value)
    }

  def order(books: List[Book], ascending: Boolean): List[Book] =
    if (!ascending)
      books.reverse
    else
      books

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
