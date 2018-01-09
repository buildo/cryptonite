package cryptonite.model

import io.buildo.enumero.annotations.indexedEnum

@indexedEnum trait Currency {
  type Index = CurrencyInfo
  object Bitcoin { CurrencyInfo ("BTC", true) }
  object BitcoinCash { CurrencyInfo ("BCH", true) }
  object Litecoin { CurrencyInfo ("LTC", true) }
  object Ethereum { CurrencyInfo ("ETH", true) }
  object Ripple { CurrencyInfo ("XRP", true) }
  object Cardano { CurrencyInfo("ADA", true) }
  object NEM { CurrencyInfo("XEM", true) }
  object Stellar { CurrencyInfo("XLM", true) }
  object IOTA { CurrencyInfo("IOTA", true) }
  object TRON { CurrencyInfo("TRX", true) }
  object Dash { CurrencyInfo("DASH", true) }
  object NEO { CurrencyInfo("NEO", true) }
  object Monero { CurrencyInfo("XMR", true) }
  object EOS { CurrencyInfo("EOS", true) }
  object ICON { CurrencyInfo("ICX", true) }
  object Qtum { CurrencyInfo("QTUM", true) }
  object BitcoinGold { CurrencyInfo("BTG", true) }
  object Lisk { CurrencyInfo("LSK", true) }
  object RaiBlocks { CurrencyInfo ("XRB", true) }
  object EthereumClassic { CurrencyInfo ("ETC", true) }
  object Euro { CurrencyInfo ("EUR", false) }
  object Dollar { CurrencyInfo ("USD", false) }
  object Pound { CurrencyInfo ("GBP", false) }
}
