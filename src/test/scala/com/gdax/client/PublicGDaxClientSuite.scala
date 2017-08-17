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

  test("publicClient.topBook should return a the top book"){
    val book: Either[ErrorCode, Book] = Await.result(client.topBook("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
    assert(book.right.get.bids.size == 1 && book.right.get.asks.size == 1)
  }

  test("publicClient.top50Books should return a the top books"){
    val book: Either[ErrorCode, Book] = Await.result(client.top50Books("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
    assert(book.right.get.bids.size > 0 && book.right.get.asks.size > 0)
  }

  test("publicClient.fullBooks should return the full ledger"){
    val book: Either[ErrorCode, FullBook] = Await.result(client.fullBooks("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
    assert(book.right.get.bids.size > 0 && book.right.get.asks.size > 0)
  }

  test("publicClient.time should return the time"){
    val time: Either[ErrorCode, Time] = Await.result(client.time(), 1.seconds)
    println(time)
    assert(time.isRight)
  }

  test("publicClient.ticker should return a valid ticker"){
    val ticker: Either[ErrorCode, Ticker] = Await.result(client.ticker("BTC-USD"), 1.seconds)
    println(ticker)
    assert(ticker.isRight)
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
//failing
  test("publicClient.currencies should return a valid currency"){
    val currencies: Either[ErrorCode, List[Currencies]] = Await.result(client.currencies(), 1.seconds)
    println(currencies)
    assert(currencies.isRight)
  }

  test("publicClient.candles should return correct candles"){
    val candles: List[Candle] = Await.result(client.candles("BTC-USD", Instant.parse("2017-08-15T00:46:23Z"), Instant.parse("2017-08-15T01:46:23Z"), 200), 1.seconds).right.get
    val rawJson = """[[1502760000,3800,3800,3800,3800,0.5188195999999999],[1502759400,129.54,3800,3800,3800,1.49940243],[1502759000,3800,3800,3800,3800,0.00262501]]"""
    import com.gdax.models.ImplicitsReads._
    val expectedCandle = Json.parse(rawJson).as[List[Candle]]
    assert(expectedCandle == candles)
  }
}
