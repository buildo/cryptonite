package cryptonite.model

import io.buildo.enumero.annotations.indexedEnum

@indexedEnum trait Currency {
  type Index = CurrencyInfo
  object Bitcoin { CurrencyInfo ("BTC", true) }
  object BitcoinCash { CurrencyInfo ("BCH", true) }
  object Litecoin { CurrencyInfo ("LTC", true) }
  object Ethereum { CurrencyInfo ("ETH", true) }
  object Euro { CurrencyInfo ("EUR", false) }
  object Dollar { CurrencyInfo ("USD", false) }
}
