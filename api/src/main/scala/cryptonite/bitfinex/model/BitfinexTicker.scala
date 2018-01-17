package cryptonite.bitfinex.model

case class BitfinexTicker (
  symbol: String,
  bid: Float,
  bidSize: Float,
  ask: Float,
  askSize: Float
)
