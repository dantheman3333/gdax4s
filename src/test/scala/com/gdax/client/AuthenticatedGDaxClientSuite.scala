package com.gdax.client

import java.time.Instant

import com.gdax.error._
import com.gdax.models.OrderParams.Side
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


  test("authenticatedClient.accounts should return correct accounts") {
    val accounts: Either[ErrorCode, List[Account]] = Await.result(client.accounts(), 5.seconds)
    println(accounts)
    assert(accounts.right.get.size > 0)
  }

  test("authenticatedClient.account should return correct account") {
    val accounts: List[Account] = Await.result(client.accounts(), 5.seconds).right.get
    val account: Either[ErrorCode, AccountWithProfile] = Await.result(client.account(accounts.head.id), 5.seconds)
    println(accounts)
    assert(account.isRight)
  }

  test("authenticatedClient.PaymentMethod should return correct Payment Methods") {
    val paymentMethods: Either[ErrorCode, List[PaymentMethod]] = Await.result(client.paymentMethods(), 5.seconds)
    println(paymentMethods)
    assert(paymentMethods.isRight)
  }

  test("authenticatedClient.coinbaseAccounts should return correct coinbase accounts") {
    val coinbaseAccounts: Either[ErrorCode, List[CoinBaseAccount]] = Await.result(client.coinbaseAccounts(), 5.seconds)
    println(coinbaseAccounts)
    assert(coinbaseAccounts.isRight)
  }

  //test("authenticatedClient.depositFromPaymentMethod should deposit") {
    //val paymentMethods: List[PaymentMethod] = Await.result(client.paymentMethods(), 5.seconds).right.get.filter(_.limits.deposit.isDefined)
    //val payments = paymentMethods.map(payment => Await.result(client.depositFromPaymentMethod(payment.limits.deposit.head.head.remaining.amount, "USD", payment.currency), 30.seconds))
    //payments.foreach(println)
    //payments.foreach(pay => assert(pay.isRight))
  //}

  test("authenticatedClient.depositFromCoinBaseAccount should deposit") {
    val coinbaseAccount: CoinBaseAccount = Await.result(client.coinbaseAccounts(), 5.seconds).right.get.filter(_.balance > 10).head
    val deposit: Either[ErrorCode, CoinBaseDeposit] = Await.result(client.depositFromCoinbaseAccount(10.00, coinbaseAccount.currency, coinbaseAccount.id), 5.seconds)
    println(deposit)
    assert(deposit.isRight)
  }

  test("authenticatedClient.limitOrder sell should create a sell order") {
    val orderResponse: Either[ErrorCode, LimitOrderResponse] = Await.result(client.limitOrder("BTC-USD", Side.Sell, 4000.0, 2.0), 5.seconds)
    println(orderResponse)
    assert(orderResponse.isRight)
  }

  test("authenticatedClient.marketOrder sell should create a sell order") {
    val orderResponse: Either[ErrorCode, MarketAndStopOrderResponse] = Await.result(client.marketOrder("BTC-USD", Side.Sell, size = Some(2.0)), 5.seconds)
    println(orderResponse)
    val orderResponseFunds: Either[ErrorCode, MarketAndStopOrderResponse] = Await.result(client.marketOrder("BTC-USD", Side.Sell, funds = Some(5.0)), 5.seconds)

    assert(orderResponse.isRight)
    assert(orderResponseFunds.isRight)
  }

  test("authenticatedClient.stopOrder sell should create a sell order") {
    val orderResponse: Either[ErrorCode, MarketAndStopOrderResponse] = Await.result(client.stopOrder("BTC-USD", Side.Sell, price = 2000.0, size = Some(2.0)), 5.seconds)
    println(orderResponse)
    val orderResponseFunds: Either[ErrorCode, MarketAndStopOrderResponse] = Await.result(client.stopOrder("BTC-USD", Side.Sell, price = 2000.0, funds = Some(5.0)), 5.seconds)

    assert(orderResponse.isRight)
    assert(orderResponseFunds.isRight)
  }

}