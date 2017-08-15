package com.gdax.client

import com.gdax.error._
import com.gdax.models.GDaxProduct
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

    assert(products.isRight)
    assert(products.right.get.size > 0)
  }
}
