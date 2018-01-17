package cryptonite.kraken.model

case class KrakenProduct (
  id: String,
  base: KrakenCurrency,
  quote: KrakenCurrency
)
