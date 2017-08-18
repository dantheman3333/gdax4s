package com.gdax.client

import java.time.Instant

import com.gdax.error._
import com.gdax.models._
import org.scalatest.{BeforeAndAfter, FunSuite}
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global

class AuthenticatedGDaxClientSuite extends FunSuite with BeforeAndAfter {

  var client: AuthenticatedGDaxClient = _

  before {
    client = AuthenticatedGDaxClient(SandBoxUrl)
  }

  after {
    client.close()
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
    assert(book.right.get.bids.size > 0 && book.right.get.asks.size > 0)
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
    val candles: Either[ErrorCode, List[Candle]] = Await.result(client.candles("BTC-USD", Instant.parse("2017-08-15T00:46:23Z"), Instant.parse("2017-08-15T01:46:23Z"), 200), 1.seconds)
    println(candles)
    assert(candles.right.get.size > 0)
  }

  test("authenticatedClient.accounts should return correct account"){
    val accounts: Either[ErrorCode, List[Accounts]] = Await.result(client.accounts(), 1.seconds)
    println(accounts)
    assert(accounts.right.get.size > 0)
  }
//missing account id
  test("authenticatedClient.account should return correct account"){
    val accounts: Either[ErrorCode, Account] = Await.result(client.account("accountid"), 1.seconds)
    println(accounts)
    assert(accounts.isRight)
  }

}