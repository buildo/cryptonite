package cryptonite.model

case class Book (
  exchange: Exchange,
  product: Product,
  bid: Amount,
  ask: Amount,
  value: Amount
)
