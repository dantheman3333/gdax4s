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