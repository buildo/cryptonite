package cryptonite.gdax.model

case class GDAXTicker (
  trade_id: Int,
  price: Float,
  size: Float,
  bid: Float,
  ask: Float,
  volume: Float,
  time: String
)
