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
    val currencies = CaseEnumSerialization[Currency].values.toList.sortBy(_.index.code)
    if (currencies.sameElements(amounts.map(_.currency).sortBy(_.index.code)))
      Right(repository.save(amounts))
    else
      Left(ApiError.PortfolioMismatchError)
  }
}
