package cryptonite.bitfinex.model

case class BitfinexTicker (
  trade_id: Int,
  price: Float,
  size: Float,
  bid: Float,
  ask: Float,
  volume: Float,
  time: String
)
