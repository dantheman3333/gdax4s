package com.gdax.util

import java.time.Instant

import org.scalatest.FunSuite
import scala.concurrent.duration.DurationInt

class CandleUtilitySuite extends FunSuite {

  //doesn't actually test to see if it's right
  test("CandleUtility.writeCandles should write out a file with correct candles"){
    val start = Instant.parse("2017-06-18T00:46:23Z")
    val end = Instant.parse("2017-08-18T00:46:23Z")
    val candles = CandleUtility.getAllCandles("BTC-USD", start, end, 1.hour.toSeconds)
    CandleUtility.writeCandlesToCsv("target/candles1h.csv", candles)
  }
}
