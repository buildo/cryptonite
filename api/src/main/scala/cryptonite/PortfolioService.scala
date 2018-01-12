package cryptonite

import scala.concurrent.{Future, ExecutionContext}

import cryptonite.model.Amount
import cryptonite.errors.ApiError

class PortfolioService(repository: PortfolioRepository)(implicit ec: ExecutionContext) {

  def read(): Future[Either[ApiError, List[Amount]]] = Future {
    Right(repository.read())
  }

  def get(code: String): Future[Either[ApiError, Amount]] = Future {
    repository.get(code) match {
      case Some(amount) => Right(amount)
      case None => Left(ApiError.GenericError)
    }
  }
}
