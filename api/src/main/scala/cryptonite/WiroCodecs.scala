package cryptonite

import cryptonite.errors.ApiError

import wiro.server.akkaHttp._
import wiro.server.akkaHttp.FailSupport._

import akka.http.scaladsl.model._

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import io.buildo.enumero.circe._

trait WiroCodecs {
  implicit def apiErrorToResponse: ToHttpResponse[ApiError] = error =>
    error match { 
      case ApiError.GenericError => HttpResponse(
        status = StatusCodes.InternalServerError,
        entity = error
      )
    }

  private implicit def entityToJson[E](entity: E)(implicit encoder: Encoder[E]): HttpEntity.Strict = HttpEntity(
    ContentTypes.`application/json`, entity.asJson.noSpaces
  )
}
