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
}
