package cryptonite.errors

import io.buildo.enumero.annotations.enum

@enum trait ApiError {
  object GenericError
  object GDAXError
  object KrakenError
  object BitfinexError
  object BitstampError
  object CurrencyNotFoundError
  object PortfolioMismatchError
}
