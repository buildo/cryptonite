package cryptonite

import scala.concurrent.Future
import wiro.annotation._

import cryptonite.model._
import cryptonite.errors.ApiError

@path("products")
trait ProductsController {

  @query
  def read(): Future[Either[ApiError, List[Book]]]
}

class ProductsControllerImpl(service: ProductsService) extends ProductsController {

  override def read(): Future[Either[ApiError, List[Book]]] = {
    service.read()
  }

}

