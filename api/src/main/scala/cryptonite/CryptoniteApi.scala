package apiseed

import scala.concurrent.{Future, ExecutionContext}
import wiro.annotation._

@path("cryptonite")
trait CryptoniteApi {

  @query
  def helloworld(): Future[Either[Throwable, String]]
}

class CryptoniteApiImpl(implicit ec: ExecutionContext) extends CryptoniteApi {

  override def helloworld(): Future[Either[Throwable, String]] = Future {
    Right("Hello world!")
  }
}

