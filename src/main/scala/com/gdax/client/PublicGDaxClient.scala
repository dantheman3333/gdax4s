package com.gdax.client

import com.gdax.error._
import com.gdax.models.GDaxProduct
import play.api.libs.json.{JsError, JsSuccess, Json}
import com.gdax.models.ImplicitsReads._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PublicGDaxClient(url: String) extends GDaxClient(url) {

  def products(): Future[Either[ErrorCode, List[GDaxProduct]]] = {
    ws.url(url + "/products").get().map(response => {
      logger.debug(s"Received response: ${response.body}")
      Json.parse(response.body).validate[List[GDaxProduct]] match {
        case success: JsSuccess[List[GDaxProduct]] => Right(success.value)
        case JsError(e) => Left(InvalidJson(e.toString()))
      }
    })
  }
}

object PublicGDaxClient {
  def apply(url: String): PublicGDaxClient = new PublicGDaxClient(url)
}
