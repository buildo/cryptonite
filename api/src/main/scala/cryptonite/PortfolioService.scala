package cryptonite

import scala.concurrent.{Future, ExecutionContext}

import io.buildo.enumero.CaseEnumSerialization

import cryptonite.model.{ Amount, Currency }
import cryptonite.errors.ApiError

class PortfolioService(repository: PortfolioRepository)(implicit ec: ExecutionContext) {

  def read(): Future[Either[ApiError, List[Amount]]] = Future {
    Right(repository.read())
  }

  def get(code: String): Future[Either[ApiError, Amount]] = Future {
    repository.get(code) match {
      case Some(amount) => Right(amount)
      case None => Left(ApiError.CurrencyNotFoundError)
    }
  }

  def save(amounts: List[Amount]): Future[Either[ApiError, Unit]] = Future {
    val currenciesSet = CaseEnumSerialization[Currency].values
    val currenciesSize = currenciesSet.size
    (amounts.size, amounts.map(_.currency).toSet) match {
      case (currenciesSize, currenciesSet) => Right(repository.save(amounts))
      case _ => Left(ApiError.PortfolioMismatchError)
    }
  }
}
