package com.gdax.com.gdax.client

import scala.concurrent.ExecutionContext.Implicits.global //necessary
import com.gdax.com.gdax.error.{ErrorCode, InvalidJson}
import com.gdax.models.GDaxProduct
import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.concurrent.Future

class PublicGDaxClient(url: String) extends GDaxClient(url) {

  def products(): Future[Either[ErrorCode, Array[GDaxProduct]]] = {
    ws.url(url + "/products").get().map(response => {
      logger.debug(s"Received response: ${response.body}")
      Json.parse(response.body).validate[Array[GDaxProduct]] match {
        case success: JsSuccess[Array[GDaxProduct]] => Right(success.value)
        case JsError(e) => Left(InvalidJson(e.toString()))
      }
    })
  }
}

object PublicGDaxClient {
  def apply(url: String): PublicGDaxClient = new PublicGDaxClient(url)
}
