package cryptonite.errors

import io.buildo.enumero.annotations.enum

@enum trait ApiError {
  object GenericError
  object CurrencyNotFoundError
}
