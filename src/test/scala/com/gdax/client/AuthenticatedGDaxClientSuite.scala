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
    client = AuthenticatedGDaxClient(SandboxUrl)
  }

  after {
    client.close()
  }


  test("authenticatedClient.accounts should return correct account"){
    val accounts: Either[ErrorCode, List[Account]] = Await.result(client.accounts(), 1.seconds)
    println(accounts)
    assert(accounts.right.get.size > 0)
  }
//missing account id
  test("authenticatedClient.account should return correct account"){
    val accounts: List[Account] = Await.result(client.accounts(), 1.seconds).right.get
    val account: Either[ErrorCode, AccountWithProfile] = Await.result(client.account(accounts.head.id), 1.seconds)
    println(accounts)
    assert(account.isRight)
  }

}