package cryptonite.model.exchange

import cryptonite.model.{Amount, Product}

case class Ticker (
  product: Product,
  bid: Amount,
  ask: Amount
)
