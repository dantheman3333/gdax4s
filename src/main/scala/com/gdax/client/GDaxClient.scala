package com.gdax.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory
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
}