package cryptonite.gdax.model

import cryptonite.model.{Amount, Product}

case class Ticker (
  product: Product,
  bid: Amount,
  ask: Amount
)
