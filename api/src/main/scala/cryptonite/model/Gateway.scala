package cryptonite.model

import scala.concurrent.Future

import cryptonite.errors.ApiError
import cryptonite.model.exchange.Ticker

trait Gateway {
  def read(): Future[Either[ApiError, List[Ticker]]]

  def exchange(): Exchange
}
