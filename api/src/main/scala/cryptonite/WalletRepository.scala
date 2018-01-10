package cryptonite

import scala.collection.concurrent.TrieMap
import io.buildo.enumero.CaseEnumSerialization
import cryptonite.model.{Amount, Currency}

class WalletRepository {
  private val wallet: TrieMap[String, Amount] = initializeWallet()

  def read(): List[Amount] = wallet.values.toList

  private def initializeWallet(): TrieMap[String, Amount] = {
    val wallet: TrieMap[String, Amount] = new TrieMap()
    val currencies: Set[Amount] = CaseEnumSerialization[Currency].values.map(new Amount(0.0F,_))
    currencies.filter(_.currency.index.isCrypto).foreach {
      x => wallet.put(x.currency.index.code, x)
    }
    wallet
  }
}
