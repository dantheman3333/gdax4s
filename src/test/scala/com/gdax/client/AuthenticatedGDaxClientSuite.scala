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
    val accounts: Either[ErrorCode, List[Account]] = Await.result(client.accounts(), 5.seconds)
    println(accounts)
    assert(accounts.right.get.size > 0)
  }

  test("authenticatedClient.account should return correct account"){
    val accounts: List[Account] = Await.result(client.accounts(), 5.seconds).right.get
    val account: Either[ErrorCode, AccountWithProfile] = Await.result(client.account(accounts.head.id), 5.seconds)
    println(accounts)
    assert(account.isRight)
  }

  test("authenticatedClient.PaymentMethod should return correct Payment Methods"){
    val paymentMethods: Either[ErrorCode, List[PaymentMethod]] = Await.result(client.paymentMethods(), 5.seconds)
    println(paymentMethods)
    assert(paymentMethods.isRight)
  }

  test("authenticatedClient.coinbaseAccounts should return correct coinbase accounts"){
    val coinbaseAccounts: Either[ErrorCode, List[CoinBaseAccount]] = Await.result(client.coinbaseAccounts(), 5.seconds)
    println(coinbaseAccounts)
    assert(coinbaseAccounts.isRight)
  }

  test("authenticatedClient.depositFromPaymentMethod should deposit"){
    val paymentMethod: PaymentMethod = Await.result(client.paymentMethods(), 5.seconds).right.get.filter(_.currency == "USD").head
    val deposit: Either[ErrorCode, PaymentMethodDeposit] = Await.result(client.depositFromPaymentMethod(10.00, "USD", paymentMethod.id), 30.seconds)
    println(deposit)
    assert(deposit.isRight)
  }

  test("authenticatedClient.depositFromCoinBaseAccount should deposit"){
    val coinbaseAccount: CoinBaseAccount = Await.result(client.coinbaseAccounts(), 5.seconds).right.get.filter(_.balance > 10).head
    val deposit: Either[ErrorCode, CoinBaseDeposit] = Await.result(client.depositFromCoinbaseAccount(10.00, coinbaseAccount.currency, coinbaseAccount.id), 5.seconds)
    println(deposit)
    assert(deposit.isRight)
  }

}