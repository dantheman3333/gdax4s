package com.gdax.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.gdax.error._
import com.gdax.models.ImplicitsReads._
import com.gdax.models.ResponseType
import org.slf4j.LoggerFactory
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.ahc._

import scala.concurrent.Future

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

  def isValidResponse(statusCode: Int): Boolean = if(statusCode == 200) true else false
}