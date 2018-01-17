package cryptonite.kraken.model

import io.buildo.enumero.annotations.enum

import cryptonite.model.Currency

@enum trait KrakenCurrency {
  BCH
  DASH
  EOS
  GNO
  KFEE
  USDT
  XDAO
  XETC
  XETH
  XICN
  XLTC
  XMLN
  XNMC
  XREP
  XXBT
  XXDG
  XXLM
  XXMR
  XXRP
  XXVN
  XZEC
  ZCAD
  ZEUR
  ZGBP
  ZJPY
  ZKRW
  ZUSD
}

object KrakenCurrencies {

  def convertCurrency(currency: KrakenCurrency): Option[Currency] = {
    currency match {
      case KrakenCurrency.BCH => Some(Currency.BitcoinCash)
      case KrakenCurrency.DASH => Some(Currency.Dash)
      case KrakenCurrency.EOS => Some(Currency.EOS)
      case KrakenCurrency.XETC => Some(Currency.EthereumClassic)
      case KrakenCurrency.XETH => Some(Currency.Ethereum)
      case KrakenCurrency.XICN => Some(Currency.ICON)
      case KrakenCurrency.XLTC => Some(Currency.Litecoin)
      case KrakenCurrency.XXBT => Some(Currency.Bitcoin)
      case KrakenCurrency.XXLM => Some(Currency.Stellar)
      case KrakenCurrency.XXMR => Some(Currency.Monero)
      case KrakenCurrency.XXRP => Some(Currency.Ripple)
      case KrakenCurrency.ZEUR => Some(Currency.Euro)
      case KrakenCurrency.ZGBP => Some(Currency.Pound)
      case KrakenCurrency.ZUSD => Some(Currency.Dollar)
      case _ => None
    }
  }
}
