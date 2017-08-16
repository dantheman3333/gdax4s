package com.gdax.client

import com.gdax.error._
import com.gdax.models.{Book, GDaxProduct, Time}
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

  test("get /products should return a non-empty list of products"){
    val products: Either[ErrorCode, List[GDaxProduct]] = Await.result(client.products(), 1.seconds)
    println(products)
    assert(products.isRight)
    assert(products.right.get.size > 0)
  }

  test("get /products/productID/book should return a book"){
    val book: Either[ErrorCode, Book] = Await.result(client.book("BTC-USD"), 1.seconds)
    println(book)
    assert(book.isRight)
  }

  test("get /time should return the time"){
    val time: Either[ErrorCode, Time] = Await.result(client.time(), 1.seconds)
    println(time)
    assert(time.isRight)
  }
}
