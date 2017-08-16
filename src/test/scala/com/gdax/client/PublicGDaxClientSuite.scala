package com.gdax.client

import com.gdax.error._
import com.gdax.models._
import org.scalatest.{BeforeAndAfter, FunSuite}

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
}
