package cryptonite

import wiro.server.akkaHttp._

import akka.http.scaladsl.model._

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import io.buildo.enumero.circe._

import cryptonite.errors.ApiError

trait WiroCodecs {
  implicit def apiErrorToResponse: ToHttpResponse[ApiError] = error =>
    error match {
      case ApiError.GenericError => HttpResponse(
        status = StatusCodes.InternalServerError,
        entity = error
      )
      case ApiError.CurrencyNotFoundError => HttpResponse(
        status = StatusCodes.InternalServerError,
        entity = error
      )
      case ApiError.PortfolioMismatchError => HttpResponse(
        status = StatusCodes.BadRequest,
        entity = error
      )
    }

  private implicit def entityToJson[E](entity: E)(implicit encoder: Encoder[E]): HttpEntity.Strict = HttpEntity(
    ContentTypes.`application/json`, entity.asJson.noSpaces
  )
}
