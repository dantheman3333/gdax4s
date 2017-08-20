package com.gdax.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.gdax.error.{ErrorCode, InvalidJson, RequestError}
import org.slf4j.LoggerFactory
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.libs.ws.StandaloneWSResponse
import play.api.libs.ws.ahc._

abstract class GDaxClient(url: String) {

  val logger = LoggerFactory.getLogger(this.getClass.getName)

  private implicit val system = ActorSystem()
  system.registerOnTermination {
    System.exit(0)
  }
  private implicit val materializer = ActorMaterializer()

  // Create the standalone WS client
  // no argument defaults to a AhcWSClientConfig created from
  // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
  val ws = StandaloneAhcWSClient()

  def close() = {
    ws.close()
  }

  protected def parseResponse[A: Reads](response: StandaloneWSResponse): Either[ErrorCode, A] = {
    if (isValidResponse(response.status)) {
      logger.debug(s"Received response: ${response.body}")
      println(s"Received response: ${response.body}")
      Json.parse(response.body).validate[A] match {
        case success: JsSuccess[A] => Right(success.value)
        case JsError(e) => Left(InvalidJson(e.toString()))
      }
    } else {
      logger.debug(s"Response Error: ${response.status}.")
      Left(RequestError(response.status, response.body))
    }
  }

  def isValidResponse(statusCode: Int): Boolean = if(statusCode == 200) true else false
}