package cryptonite.bitfinex.model

import io.buildo.enumero.annotations.enum
import io.buildo.enumero.CaseEnumSerialization

import cryptonite.model.Currency

@enum trait BitfinexCurrency {
  USD
  BTC
  ETH
  EUR
  LTC
  ETC
  RRT
  ZEC
  XMR
  DSH
  XRP
  IOT
  EOS
  SAN
  OMG
  BCH
  NEO
  ETP
  QTM
  AVT
  EDO
  BTG
  DAT
  QSH
  YYW
  GNT
  SNT
  BAT
  MNA
  FUN
  ZRX
  TNB
  SPK
}

object BitfinexCurrencies {
  def convertCurrency(currency: String): Option[Currency] = {
    val bitfinexCurrency = CaseEnumSerialization[BitfinexCurrency].caseFromString(currency)
    bitfinexCurrency match {
      case Some(BitfinexCurrency.USD) => Some(Currency.Dollar)
      case Some(BitfinexCurrency.BTC) => Some(Currency.Bitcoin)
      case Some(BitfinexCurrency.ETH) => Some(Currency.Ethereum)
      case Some(BitfinexCurrency.EUR) => Some(Currency.Euro)
      case Some(BitfinexCurrency.LTC) => Some(Currency.Litecoin)
      case Some(BitfinexCurrency.ETC) => Some(Currency.EthereumClassic)
      case Some(BitfinexCurrency.XMR) => Some(Currency.Monero)
      case Some(BitfinexCurrency.DSH) => Some(Currency.Dash)
      case Some(BitfinexCurrency.XRP) => Some(Currency.Ripple)
      case Some(BitfinexCurrency.IOT) => Some(Currency.IOTA)
      case Some(BitfinexCurrency.EOS) => Some(Currency.EOS)
      case Some(BitfinexCurrency.BCH) => Some(Currency.BitcoinCash)
      case Some(BitfinexCurrency.NEO) => Some(Currency.NEO)
      case Some(BitfinexCurrency.QTM) => Some(Currency.Qtum)
      case Some(BitfinexCurrency.BTG) => Some(Currency.BitcoinGold)
      case _ => None
    }
  }
}
