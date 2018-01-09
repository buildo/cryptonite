package cryptonite

import scala.concurrent.{Future, ExecutionContext}
import wiro.annotation._
import cryptonite.model._
import cryptonite.errors.ApiError

@path("cryptonite/wallet")
trait WalletController {

  @query
  def read(): Future[Either[ApiError, List[Amount]]]
}

class WalletControllerImpl(implicit ec: ExecutionContext) extends WalletController {

  override def read(): Future[Either[ApiError, List[Amount]]] = Future {
    Left(ApiError.GenericError)
  }
}

