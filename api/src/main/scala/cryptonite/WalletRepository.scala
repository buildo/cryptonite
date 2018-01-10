package cryptonite

import cryptonite.model.{Amount, Currency}
import scala.collection.concurrent.TrieMap
import io.buildo.enumero.CaseEnumSerialization

class WalletRepository {
  val wallet: TrieMap[String, Amount] = initializeWallet()

  def read(): List[Amount] = wallet.values.toList

  def initializeWallet(): TrieMap[String, Amount] = {
    val wallet: TrieMap[String, Amount] = new TrieMap()
    val currencies: Set[Amount] = CaseEnumSerialization[Currency].values.map(new Amount(0.0F,_))
    currencies.foreach(x => if(x.currency.index.isCrypto){wallet.put(x.currency.index.code, x)})
    wallet
  }
}
