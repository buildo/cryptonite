package cryptonite

import scala.concurrent.Future

import org.scalatest._
import org.scalatest.Matchers._

import cryptonite.model.{ Amount, Currency }

class PortfolioServiceSpec extends fixture.AsyncFlatSpec with EitherValues {

  val amountsToBeSaved = List(
    new Amount(10.0F, Currency.Bitcoin),
    new Amount(5.374F, Currency.BitcoinCash),
    new Amount(34.23F, Currency.Litecoin),
    new Amount(8.9231F, Currency.Ethereum),
    new Amount(3.474243F, Currency.Ripple),
    new Amount(5.329874F, Currency.Cardano),
    new Amount(2.374F, Currency.NEM),
    new Amount(200.15F, Currency.Stellar),
    new Amount(1900.234F, Currency.IOTA),
    new Amount(1738.0F, Currency.TRON),
    new Amount(143.0F, Currency.Dash),
    new Amount(972.0F, Currency.NEO),
    new Amount(2.0F, Currency.Monero),
    new Amount(34.0F, Currency.EOS),
    new Amount(7.5F, Currency.ICON),
    new Amount(3.4F, Currency.Qtum),
    new Amount(3.3F, Currency.BitcoinGold),
    new Amount(70.5F, Currency.Lisk),
    new Amount(10.2F, Currency.RaiBlocks),
    new Amount(19.2F, Currency.EthereumClassic)
  )

  type FixtureParam = PortfolioService

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    val service = new PortfolioService(new PortfolioRepository())
    withFixture(test.toNoArgAsyncTest(service))
  }

  "The portfolio service" should "return a list of amounts of supported cryptocurrencies when the read method is invoked" in { service =>
    service.read().checkFutureResult(_ should have size 20)
  }

  "The portfolio service" should "should successfully save a valid list of amounts in the portfolio" in { service =>
    service.save(amountsToBeSaved).checkFutureResult(_ => succeed)
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
