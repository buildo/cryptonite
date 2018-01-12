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

class ProductsControllerImpl(service: ProductsService)(implicit ec: ExecutionContext) extends ProductsController {

  override def read(): Future[Either[ApiError, List[Book]]] = {
    service.read()
  }

}

