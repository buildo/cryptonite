package cryptonite.gdax.model

case class GDAXProduct (
  id: String,
  base_currency: String,
  quote_currency: String,
  base_min_size: Float,
  base_max_size: Float,
  quote_increment: Float,
)
