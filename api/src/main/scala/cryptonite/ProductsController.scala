package cryptonite

import scala.concurrent.{Future, ExecutionContext}
import wiro.annotation._

import cryptonite.model._
import cryptonite.errors.ApiError

@path("products")
trait ProductsController {

  @query
  def read(): Future[Either[ApiError, List[Book]]]
}

class ProductsControllerImpl(implicit ec: ExecutionContext) extends ProductsController {

  override def read(): Future[Either[ApiError, List[Book]]] = Future(Right(List(
    new Book(Exchange.GDAX, new Product(Currency.Bitcoin, Currency.Euro), new Amount(11948.02F, Currency.Euro), new Amount(11948.08F, Currency.Euro), new Amount(5974.01F, Currency.Euro)),
    new Book(Exchange.GDAX, new Product(Currency.Bitcoin, Currency.Pound), new Amount(10488.03F, Currency.Pound), new Amount(10516.72F, Currency.Pound), new Amount(5244.015F, Currency.Pound)),
    new Book(Exchange.GDAX, new Product(Currency.Bitcoin, Currency.Dollar), new Amount(13982.6F, Currency.Dollar), new Amount(13984.93F, Currency.Dollar), new Amount(6691.3F, Currency.Dollar)),
    new Book(Exchange.Kraken, new Product(Currency.Litecoin, Currency.Bitcoin), new Amount(0.016978F, Currency.Bitcoin), new Amount(0.017017F, Currency.Bitcoin), new Amount(0F, Currency.Bitcoin)),
    new Book(Exchange.Kraken, new Product(Currency.Bitcoin, Currency.Euro), new Amount(11763.8F, Currency.Euro), new Amount(11763.9F, Currency.Euro), new Amount(5581.9F, Currency.Euro)),
    new Book(Exchange.Kraken, new Product(Currency.Ripple, Currency.Euro), new Amount(1.68000F, Currency.Euro), new Amount(1.68698F, Currency.Euro), new Amount(16.8F, Currency.Euro))
  )))

}

