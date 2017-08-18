package com.gdax.client

import java.time.Instant

import com.gdax.error._
import com.gdax.models._
import org.scalatest.{BeforeAndAfter, FunSuite}
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class PublicGDaxClientSuite extends FunSuite with BeforeAndAfter {

  var client: PublicGDaxClient = _

  before {
    Thread.sleep(334) // to not spam the server
    client = PublicGDaxClient(SandBoxUrl)
  }

  after {
    client.close()
  }

  test("publicClient.products() should return a non-empty list of products"){
    val products: Either[ErrorCode, List[GDaxProduct]] = Await.result(client.products(), 1.seconds)
    println(products)
    assert(products.isRight)
    assert(products.right.get.size > 0)
  }


  test("publicClient.time should return the time"){
    val time: Either[ErrorCode, Time] = Await.result(client.time(), 1.seconds)
    println(time)
    assert(time.isRight)
  }

  test("publicClient.trades with limit 5 should return a 5 trades"){
    val trades: Either[ErrorCode, List[Trades]] = Await.result(client.trades("BTC-USD", limit = Some(5)), 1.seconds)
    println(trades)
    assert(trades.isRight)
    assert(trades.right.get.size == 5)
  }

  test("publicClient.trades before and after should return trades"){
    val before: Either[ErrorCode, List[Trades]] = Await.result(client.trades("BTC-USD", before = Some(2), limit = Some(1)), 1.seconds)
    val after: Either[ErrorCode, List[Trades]] = Await.result(client.trades("BTC-USD", after = Some(3), limit = Some(1)), 1.seconds)
    assert(before.isRight && after.isRight)
  }

  test("publicClient.currencies should return a valid currency"){
    val currencies: Either[ErrorCode, List[Currencies]] = Await.result(client.currencies(), 1.seconds)
    println(currencies)
    assert(currencies.isRight)
  }

  test("publicClient.stats should return daily stats for a valid product id"){
    val stats: Either[ErrorCode, DailyStats] = Await.result(client.dailyStats("BTC-USD"), 1.seconds)
    println(stats)
    assert(stats.isRight)
  }

  test("publicClient.topBook should return a the top book") {
    val book: Either[ErrorCode, Book] = Await.result(client.topBook("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
    assert(book.right.get.bids.size == 1 && book.right.get.asks.size == 1)
  }

  test("publicClient.top50Books should return a the top books") {
    val book: Either[ErrorCode, Book] = Await.result(client.top50Books("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
    assert(book.right.get.bids.size == 50 && book.right.get.asks.size == 50)
  }

  test("publicClient.fullBooks should return the full ledger") {
    val book: Either[ErrorCode, FullBook] = Await.result(client.fullBooks("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
    assert(book.right.get.bids.size > 0 && book.right.get.asks.size > 0)
  }

  test("publicClient.ticker should return a valid ticker") {
    val ticker: Either[ErrorCode, Ticker] = Await.result(client.ticker("BTC-USD"), 1.seconds)
    println(ticker)
    assert(ticker.isRight)
  }

  test("publicClient.candles should return correct candles"){
    import com.gdax.models.ImplicitsReads.candleReads
    val candles: List[Candle] = Await.result(client.candles("BTC-USD", Instant.parse("2017-08-15T00:46:23Z"), Instant.parse("2017-08-15T01:46:23Z"), 200), 1.seconds).right.get
    val rawJson = """[[1502761400,4349.98,4351.99,4349.98,4350.23,50.22978923999999],[1502761200,4349.97,4349.98,4349.98,4349.98,28.630313970000017],[1502761000,4347.49,4349.98,4347.5,4349.98,26.014040960000006],[1502760800,4340.98,4347.5,4340.99,4347.5,50.19325491999998],[1502760600,4338.06,4340.99,4338.07,4340.99,30.484479570000012],[1502760400,4325.62,4340.01,4340,4338.07,91.70609010000001],[1502760200,4340,4340.01,4340,4340.01,30.96785751000003],[1502760000,4340,4340.01,4340.01,4340.01,35.94444889000002],[1502759800,4340,4340.01,4340.01,4340.01,39.64375342],[1502759600,4335.94,4354.9,4354.9,4340.01,126.96300797999999],[1502759400,4354.9,4354.91,4354.91,4354.9,26.658814000000014],[1502759200,4354.88,4354.91,4354.89,4354.91,54.47993825999998],[1502759000,4354.88,4354.89,4354.89,4354.89,44.05279222],[1502758800,4354.88,4354.91,4354.89,4354.89,58.110535479999996],[1502758600,4349.02,4355.98,4349.02,4354.88,46.27414755999999],[1502758400,4349.01,4349.25,4349.25,4349.02,36.71927769999999],[1502758200,4344.71,4349.52,4344.71,4349.39,69.48470231],[1502758000,4337.99,4344.74,4338,4344.71,43.09682999000002],[1502757800,4338,4338,4338,4338,1.13014382]]"""
    val expectedCandles = Json.parse(rawJson).as[List[Candle]]
    assert(expectedCandles == candles)
  }
}
