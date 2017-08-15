package com.gdax.client

import com.gdax.error._
import com.gdax.models.GDaxProduct
import play.api.libs.json.{JsError, JsSuccess, Json}
import com.gdax.models.ImplicitsReads._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PublicGDaxClient(url: String) extends GDaxClient(url) {

  def products(): Future[Either[ErrorCode, List[GDaxProduct]]] = {
    val uri = url + "/products"
    ws.url(uri).get().map(response => {
      if (isValidResponse(response.status)) {
        logger.debug(s"Sent URI: $uri. Received response: ${response.body}")
        Json.parse(response.body).validate[List[GDaxProduct]] match {
          case success: JsSuccess[List[GDaxProduct]] => Right(success.value)
          case JsError(e) => Left(InvalidJson(e.toString()))
        }
      } else {
        logger.debug(s"Sent URI: $uri. Response Error: ${response.status}.")
        Left(RequestError(response.status))
      }
    })
  }
}

object PublicGDaxClient {
  def apply(url: String): PublicGDaxClient = new PublicGDaxClient(url)
}
