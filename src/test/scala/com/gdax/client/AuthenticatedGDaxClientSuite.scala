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


  test("authenticatedClient.accounts should return correct accounts"){
    val accounts: Either[ErrorCode, List[Account]] = Await.result(client.accounts(), 1.seconds)
    println(accounts)
    assert(accounts.right.get.size > 0)
  }

  test("authenticatedClient.account should return correct account"){
    val accounts: List[Account] = Await.result(client.accounts(), 1.seconds).right.get
    val account: Either[ErrorCode, AccountWithProfile] = Await.result(client.account(accounts.head.id), 1.seconds)
    println(accounts)
    assert(account.isRight)
  }

  test("authenticatedClient.PaymentMethod should return correct Payment Methods"){
    val paymentMethods: Either[ErrorCode, List[PaymentMethod]] = Await.result(client.paymentMethods(), 1.seconds)
    println(paymentMethods)
    assert(paymentMethods.isRight)
  }

  test("authenticatedClient.coinbaseAccounts should return correct coinbase accounts"){
    val coinbaseAccounts: Either[ErrorCode, List[CoinBaseAccount]] = Await.result(client.coinbaseAccounts(), 1.seconds)
    println(coinbaseAccounts)
    assert(coinbaseAccounts.isRight)
  }


  test("authenticatedClient.depositPaymentMethod should deposit"){
    val paymentMethod: PaymentMethod = Await.result(client.paymentMethods(), 1.seconds).right.get.filter(_.currency == "USD").head
    val deposit: Either[ErrorCode, PaymentMethodDeposit] = Await.result(client.depositPaymentMethod(10.00, "USD", paymentMethod.id), 1.seconds)
    println(deposit)
    assert(deposit.isRight)
  }

}