package com.gdax.client

import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import com.gdax.models.ImplicitsReads._
import com.gdax.error.ErrorCode
import com.gdax.models._
import play.api.libs.json.Reads
import play.api.libs.ws.StandaloneWSRequest
import play.shaded.ahc.org.asynchttpclient.util.Base64

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AuthenticatedGDaxClient(url: String) extends PublicGDaxClient(url) {

  def topBook(productId: String): Future[Either[ErrorCode, Book]] = {
    val uri = s"$url/products/$productId/book"
    authorizedRequest[Book](uri)
  }

  def top50Books(productId: String): Future[Either[ErrorCode, Book]] = {
    val uri = s"$url/products/$productId/book"
    authorizedRequest[Book](uri, ("level", "2"))
  }

  def fullBooks(productId: String): Future[Either[ErrorCode, FullBook]] = {
    val uri = s"$url/products/$productId/book"
    authorizedRequest[FullBook](uri, ("level", "3"))
  }

  def ticker(productId: String): Future[Either[ErrorCode, Ticker]] = {
    val uri = s"$url/products/$productId/ticker"
    authorizedRequest[Ticker](uri)
  }

  def account(accountId: String): Future[Either[ErrorCode, Account]] = {
    val uri = s"$url/accounts/$accountId"
    authorizedRequest[Account](uri)
  }

  def accounts(): Future[Either[ErrorCode, List[Accounts]]] = {
    val uri = s"$url/accounts/"
    authorizedRequest[List[Accounts]](uri)
  }

  private def authorizedRequest[A: Reads](uri: String, parameters: (String, String)*): Future[Either[ErrorCode, A]] = {
    logger.debug(s"Sent URI: $uri")
    val requestWithHeaders = addAuthorizationHeaders(ws.url(uri).withQueryStringParameters(parameters: _*))
    requestWithHeaders.get().map(parseResponse[A](_))
  }

  private def addAuthorizationHeaders(request: StandaloneWSRequest): StandaloneWSRequest = {
    val apiKey: String = System.getProperty("apiKey")
    val secretKey: String = System.getProperty("secretKey")
    val passphrase: String = System.getProperty("passphrase")
    addAuthorizationHeaders(apiKey, secretKey, passphrase, request)
  }

  private def addAuthorizationHeaders(apiKey: String, secretKey: String, passphrase: String, request: StandaloneWSRequest): StandaloneWSRequest = {
    val CryptoFunction = "HmacSHA256"
    val timestamp: Long = Instant.now().getEpochSecond
    val message: String = timestamp + request.method.toUpperCase + request.url + request.body.toString
    val hmacKey: Array[Byte] = Base64.decode(secretKey)
    val secretKeySpec = new SecretKeySpec(hmacKey, CryptoFunction)
    val mac: Mac = Mac.getInstance(CryptoFunction)
    mac.init(secretKeySpec)
    val signature: String = Base64.encode(mac.doFinal(message.getBytes))

    val headers = Seq(("CB-ACCESS-SIGN", signature),
      ("CB-ACCESS-TIMESTAMP", timestamp.toString),
      ("CB-ACCESS-KEY", apiKey),
      ("CB-ACCESS-PASSPHRASE", passphrase))

    request.withHttpHeaders(headers: _*)
  }
}

object AuthenticatedGDaxClient {
  def apply(url: String): AuthenticatedGDaxClient = new AuthenticatedGDaxClient(url)
}