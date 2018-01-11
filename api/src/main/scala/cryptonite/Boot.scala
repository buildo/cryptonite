package cryptonite

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import wiro.Config
import wiro.server.akkaHttp._
import wiro.server.akkaHttp.FailSupport._

import com.typesafe.config.ConfigFactory
import io.circe.generic.auto._

object Boot extends App with WiroCodecs with RouterDerivationModule {
  implicit val system = ActorSystem("cryptonite")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val walletRepo = new WalletRepository()
  val walletService = new WalletService(walletRepo)
  val walletRouter = deriveRouter[WalletController](new WalletControllerImpl(walletService))

  val productsRouter = deriveRouter[ProductsController](new ProductsControllerImpl)

  val conf = ConfigFactory.load()

  val rpcServer = new HttpRPCServer(
    config = Config(conf.getString("cryptonite.host"), conf.getInt("cryptonite.port")),
    routers = List(walletRouter,productsRouter)
  )
}
