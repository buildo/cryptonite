package cryptonite

import scala.concurrent.Future

import org.scalatest._
import org.scalatest.Matchers._

import cryptonite.errors.ApiError
import cryptonite.model.Amount

class PortfolioServiceSpec extends fixture.AsyncFlatSpec with EitherValues {

  type FixtureParam = PortfolioService

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    val service = new PortfolioService(new PortfolioRepository())
    withFixture(test.toNoArgAsyncTest(service))
  }

  "The portfolio service" should "return a list of amounts of supported cryptocurrencies when the read method is invoked" in { service =>
    service.read().checkFutureResult(_ should have size 20)
  }

  implicit private class Check[E,R](fut: Future[Either[E,R]]) {
    def checkFutureResult(g: R => Assertion): Future[Assertion] =
      fut.map(_.fold(
        error => fail(error.toString),
        g
      ))

    def checkFutureError(h: E => Assertion): Future[Assertion] =
      fut.map(_.fold(
        h,
        result => fail("Unexpected result")
      ))
  }
}
