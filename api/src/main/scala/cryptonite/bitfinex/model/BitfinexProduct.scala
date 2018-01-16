package cryptonite.bitfinex.model

case class BitfinexProduct (
  id: String,
  base_currency: String,
  quote_currency: String,
  base_min_size: Float,
  base_max_size: Float,
  quote_increment: Float,
)
