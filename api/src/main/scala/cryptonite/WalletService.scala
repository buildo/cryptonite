package cryptonite

import scala.concurrent.{Future, ExecutionContext}

import cryptonite.model.Amount
import cryptonite.errors.ApiError

class WalletService(repository: WalletRepository)(implicit ec: ExecutionContext) {

  def read(): Future[Either[ApiError, List[Amount]]] = Future {
    Right(repository.read())
  }
}
