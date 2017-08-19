package com.gdax.util

import java.time.{Duration, Instant}

import com.gdax.client.PublicGDaxClient
import com.gdax.models.Candle
import org.slf4j.LoggerFactory
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object CandleUtility {

  val logger = LoggerFactory.getLogger(this.getClass.getName)
  val publicClient = PublicGDaxClient("https://api.gdax.com")

  def getAllCandles(productId: String, start: Instant, end: Instant, granularityInSeconds: Long): List[Candle] = {
    val secondsDiff = Duration.between(start, end).abs.getSeconds

    (0L until secondsDiff by (200*granularityInSeconds)).flatMap{ seconds =>
      Thread.sleep(334) //avoid rate limit of 3 per second
      val nextStart = start.plusSeconds(seconds)
      val nextEnd = start.plusSeconds(seconds + 200*granularityInSeconds)
      Await.result(publicClient.candles(productId, nextStart, nextEnd, granularityInSeconds), 2.seconds).getOrElse(Nil)
    }.toList
  }

  def writeCandlesToCsv(path: String, candles: List[Candle]) = {
    import java.io._
    val pw = new PrintWriter(new File(path))
    pw.write("time,low,high,open,close,volume" + System.lineSeparator)
    candles.foreach(candle => pw.write(candle.productIterator.mkString(",") + System.lineSeparator))
    pw.close
  }

}
