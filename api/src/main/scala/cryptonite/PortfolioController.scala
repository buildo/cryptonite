package cryptonite

import scala.concurrent.Future
import wiro.annotation._

import cryptonite.model._
import cryptonite.errors.ApiError

@path("portfolio")
trait PortfolioController {

  @query
  def read(): Future[Either[ApiError, List[Amount]]]

  @command
  def save(amounts: List[Amount]): Future[Either[ApiError, Unit]]
}

class PortfolioControllerImpl(service: PortfolioService) extends PortfolioController {

  override def read(): Future[Either[ApiError, List[Amount]]] = service.read()

  override def save(amounts: List[Amount]): Future[Either[ApiError, Unit]] = service.save(amounts)
}

