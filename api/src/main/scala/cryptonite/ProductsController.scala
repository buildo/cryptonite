package cryptonite

import scala.concurrent.Future

import wiro.annotation._

import cryptonite.model._
import cryptonite.errors.ApiError

@path("products")
trait ProductsController {

  @query
  def read(
    exchanges: List[Exchange] = List(Exchange.Kraken,Exchange.Bitfinex,Exchange.Bitstamp),
    base: Option[Currency] = None,
    quote: Option[Currency] = None,
    sortBy: Column = Column.Exchange,
    ascending: Boolean = true
  ): Future[Either[ApiError, List[Book]]]
}

class ProductsControllerImpl(service: ProductsService) extends ProductsController {

  override def read(
    exchanges: List[Exchange],
    base: Option[Currency],
    quote: Option[Currency],
    sortBy: Column,
    ascending: Boolean
  ): Future[Either[ApiError, List[Book]]] = {
    service.read(exchanges, base, quote, sortBy, ascending)
  }
}

