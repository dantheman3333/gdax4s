package com.gdax.client

import java.time.Instant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import play.api.libs.ws.StandaloneWSRequest
import play.shaded.ahc.org.asynchttpclient.util.Base64

class AuthenticatedGDaxClient(url: String) extends PublicGDaxClient(url) {

  private val CryptoFunction = "HmacSHA256"

  def addAuthorizationHeaders(request: StandaloneWSRequest): StandaloneWSRequest = {
    val apiKey: String = System.getProperty("apiKey")
    val secretKey: String = System.getProperty("secretKey")
    val passphrase: String = System.getProperty("passphrase")
    addAuthorizationHeaders(apiKey, secretKey, passphrase, request)
  }

  def addAuthorizationHeaders(apiKey: String, secretKey: String, passphrase: String, request: StandaloneWSRequest): StandaloneWSRequest = {
    val timestamp: Long = Instant.now().getEpochSecond
    val message: String = timestamp + request.method.toUpperCase + request.url + request.body.toString
    val hmacKey: Array[Byte] = Base64.decode(secretKey)
    val secretKeySpec = new SecretKeySpec(hmacKey, CryptoFunction)
    val mac: Mac = Mac.getInstance(CryptoFunction)
    mac.init(secretKeySpec)
    val signature: String = Base64.encode(mac.doFinal(hmacKey))

    val headers = Seq(("CB-ACCESS-SIGN", signature),
      ("CB-ACCESS-TIMESTAMP", timestamp.toString),
      ("CB-ACCESS-KEY", apiKey),
      ("CB-ACCESS-PASSPHRASE", passphrase))

    request.withHttpHeaders(headers:_*)
  }

}
