package cryptonite

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import wiro.Config
import wiro.server.akkaHttp._
import wiro.server.akkaHttp.FailSupport._
import com.typesafe.config.ConfigFactory
import io.circe.generic.auto._
import io.buildo.enumero.circe._

import cryptonite.gdax.GDAXGateway
import cryptonite.kraken.KrakenGateway
import cryptonite.bitfinex.BitfinexGateway
import cryptonite.bitstamp.BitstampGateway

object Boot extends App with WiroCodecs with RouterDerivationModule {
  implicit val system = ActorSystem("cryptonite")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val portfolioRepo = new PortfolioRepository()
  val portfolioService = new PortfolioService(portfolioRepo)
  val portfolioRouter = deriveRouter[PortfolioController](new PortfolioControllerImpl(portfolioService))

  val gdaxGateway = new GDAXGateway()
  val krakenGateway = new KrakenGateway()
  val bitfinexGateway = new BitfinexGateway()
  val bitstampGateway = new BitstampGateway()
  val productsService = new ProductsService(List(gdaxGateway, krakenGateway, bitfinexGateway, bitstampGateway), portfolioService)
  val productsRouter = deriveRouter[ProductsController](new ProductsControllerImpl(productsService))

  val conf = ConfigFactory.load()

  val rpcServer = new HttpRPCServer(
    config = Config(conf.getString("cryptonite.host"), conf.getInt("cryptonite.port")),
    routers = List(portfolioRouter,productsRouter)
  )
}
