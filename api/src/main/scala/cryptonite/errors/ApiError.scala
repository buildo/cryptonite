package cryptonite.errors

import io.buildo.enumero.annotations.enum

@enum trait ApiError {
  GenericError
  GDAXError
  KrakenError
  BitfinexError
  BitstampError
  CurrencyNotFoundError
  PortfolioMismatchError
}
