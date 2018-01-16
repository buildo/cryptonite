package cryptonite.bitfinex.model

import io.buildo.enumero.annotations.enum

import cryptonite.model.Currency

@enum trait BitfinexCurrency {
  USD,
  BTC,
  ETH,
  EUR,
  LTC,
  ETC,
  RRT,
  ZEC,
  XMR,
  DSH,
  XRP,
  IOT,
  EOS,
  SAN,
  OMG,
  BCH,
  NEO,
  ETP,
  QTM,
  AVT,
  EDO,
  BTG,
  DAT,
  QSH,
  YYW,
  GNT,
  SNT,
  IOT,
  BAT,
  MNA,
  FUN,
  ZRX,
  TNB,
  SPK
}

object BitfinexCurrencies {

  def convertCurrency(currency: BitfinexCurrency): Option[Currency] = {
    currency match {
      case BitfinexCurrency.USD => Some(Currency.Dollar)
      case BitfinexCurrency.BTC => Some(Currency.Bitcoin)
      case BitfinexCurrency.ETH => Some(Currency.Ethereum)
      case BitfinexCurrency.EUR => Some(Currency.Euro)
      case BitfinexCurrency.LTC => Some(Currency.Litecoin)
      case BitfinexCurrency.ETC => Some(Currency.EthereumClassic)
      case BitfinexCurrency.XMR => Some(Currency.Monero)
      case BitfinexCurrency.DSH => Some(Currency.Dash)
      case BitfinexCurrency.XRP => Some(Currency.Ripple)
      case BitfinexCurrency.IOT => Some(Currency.IOTA)
      case BitfinexCurrency.EOS => Some(Currency.EOS)
      case BitfinexCurrency.BCH => Some(Currency.BitcoinCash)
      case BitfinexCurrency.NEO => Some(Currency.NEO)
      case BitfinexCurrency.QTM => Some(Currency.Qtum)
      case BitfinexCurrency.BTG => Some(Currency.BitcoinGold)
      case _ => None
    }
  }
