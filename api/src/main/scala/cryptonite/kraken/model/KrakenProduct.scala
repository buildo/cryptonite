package cryptonite.kraken.model

import cryptonite.kraken.model.KrakenCurrency

case class KrakenProduct (
  id: String,
  base: KrakenCurrency,
  quote: KrakenCurrency
)
