package cryptonite

import scala.concurrent.{Future, ExecutionContext}
import wiro.annotation._
import cryptonite.model._
import cryptonite.errors.ApiError

@path("cryptonite")
trait CryptoniteApi {

  @query
  def getWallet(): Future[Either[ApiError, List[Amount]]]
}

class CryptoniteApiImpl(implicit ec: ExecutionContext) extends CryptoniteApi {

  override def getWallet(): Future[Either[ApiError, List[Amount]]] = Future {
    Left(ApiError.GenericError)
  }
}

