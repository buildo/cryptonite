package cryptonite

import scala.collection.concurrent.TrieMap
import io.buildo.enumero.CaseEnumSerialization
import cryptonite.model.{Amount, Currency}

class PortfolioRepository {
  private val portfolio: TrieMap[String, Amount] = initializePortfolio()

  def read(): List[Amount] = portfolio.values.toList

  def get(code: String): Option[Amount] = portfolio.get(code)

  def save(amounts: List[Amount]): Unit = amounts.foreach { x => portfolio.put(x.currency.index.code, x) }

  private def initializePortfolio(): TrieMap[String, Amount] = {
    val portfolio: TrieMap[String, Amount] = new TrieMap()
    val currencies: Set[Amount] = CaseEnumSerialization[Currency].values.map(new Amount(0.0F,_))
    currencies.filter(_.currency.index.isCrypto).foreach {
      x => portfolio.put(x.currency.index.code, x)
    }
    portfolio
  }
}
