package cryptonite.bitstamp.model

import io.buildo.enumero.annotations.enum
import io.buildo.enumero.CaseEnumSerialization

import cryptonite.model.Currency

@enum trait BitstampCurrency {
  BTC
  LTC
  ETH
  BCH
  XRP
  USD
  EUR
}

object BitstampCurrencies {
  def convertCurrency(currency: String): Option[Currency] = {
    val bitstampCurrency = CaseEnumSerialization[BitstampCurrency].caseFromString(currency)
    bitstampCurrency match {
      case Some(BitstampCurrency.BTC) => Some(Currency.Bitcoin)
      case Some(BitstampCurrency.LTC) => Some(Currency.Litecoin)
      case Some(BitstampCurrency.ETH) => Some(Currency.Ethereum)
      case Some(BitstampCurrency.BCH) => Some(Currency.BitcoinCash)
      case Some(BitstampCurrency.XRP) => Some(Currency.Ripple)
      case Some(BitstampCurrency.USD) => Some(Currency.Dollar)
      case Some(BitstampCurrency.EUR) => Some(Currency.Euro)
      case _ => None
    }
  }
}
