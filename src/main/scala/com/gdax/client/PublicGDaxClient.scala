package com.gdax.client

import com.gdax.error._
import com.gdax.models.{Book, FullBook, GDaxProduct, Time}
import com.gdax.models.ImplicitsReads._
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PublicGDaxClient(url: String) extends GDaxClient(url) {

  def products(): Future[Either[ErrorCode, List[GDaxProduct]]] = {
    val uri = s"$url/products"
    publicRequest[List[GDaxProduct]](uri)
  }

  def topBook(productId: String): Future[Either[ErrorCode, Book]] = {
    val uri = s"$url/products/$productId/book"
    publicRequest[Book](uri)
  }

  def top50Books(productId: String): Future[Either[ErrorCode, Book]] = {
    val uri = s"$url/products/$productId/book"
    publicRequest[Book](uri, ("level", "2"))
  }

  def fullBooks(productId: String): Future[Either[ErrorCode, FullBook]] = {
    val uri = s"$url/products/$productId/book"
    publicRequest[FullBook](uri, ("level", "3"))
  }

  //need implicit reader
/*
  def time(): Future[Either[ErrorCode, Time]] = {
    val uri = url + "/time"
    publicRequest[Time](uri)
  }
*/

  private def publicRequest[A: Reads](uri: String, parameters: (String, String)*): Future[Either[ErrorCode, A]] = {
    ws.url(uri).withQueryStringParameters(parameters: _*).get().map(response => {
      if (isValidResponse(response.status)) {
        logger.debug(s"Sent URI: $uri. Received response: ${response.body}")
        Json.parse(response.body).validate[A] match {
          case success: JsSuccess[A] => Right(success.value)
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
