package com.gdax.client

import java.time.Instant

import com.gdax.error._
import com.gdax.models.OrderParams.{CancelAfter, Side, TimeInForce}
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

  test("authenticatedClient.depositFromPaymentMethod should deposit") {
    val paymentMethods: List[PaymentMethod] = Await.result(client.paymentMethods(), 5.seconds).right.get
    println(paymentMethods)
    val payments = paymentMethods.filter(_.name.contains("TD Bank")).map(payment => Await.result(client.depositFromPaymentMethod(10.00, payment.currency, payment.id), 30.seconds))
    payments.foreach(println)
    payments.foreach(pay => assert(pay.isRight))
  }

  test("authenticatedClient.cancelOrder should delete an order") {
    val orderResponse: LimitOrderResponse = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 5000.0, size = 2.0, cancelAfter = Some(CancelAfter.Min), timeInForce = Some(TimeInForce.GTT)), 5.seconds).right.get
    println(orderResponse)

    val cancelOrder: CanceledOrders = Await.result(client.cancelOrder(orderResponse.id), 5.seconds).right.get
    println(cancelOrder)
    assert(cancelOrder.ids.contains(orderResponse.id))
  }

  //doesn't really test all orders
  test("authenticatedClient.cancelOrder should delete all orders") {
    val orderResponse: LimitOrderResponse = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 5000.0, size = 2.0, cancelAfter = Some(CancelAfter.Min), timeInForce = Some(TimeInForce.GTT)), 5.seconds).right.get
    Thread.sleep(500)
    val orderResponse2: LimitOrderResponse = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 6000.0, size = 2.0, cancelAfter = Some(CancelAfter.Min), timeInForce = Some(TimeInForce.GTT)), 5.seconds).right.get
    println(orderResponse)

    val cancelOrder: CanceledOrders = Await.result(client.cancelAllOrders(), 5.seconds).right.get
    println(cancelOrder)
    assert(cancelOrder.ids.contains(orderResponse.id) && cancelOrder.ids.contains(orderResponse2.id))
  }

  test("authenticatedClient.depositFromCoinBaseAccount should deposit") {
    val coinbaseAccount: CoinBaseAccount = Await.result(client.coinbaseAccounts(), 5.seconds).right.get.filter(_.balance > 10).head
    val deposit: Either[ErrorCode, CoinBaseDeposit] = Await.result(client.depositFromCoinbaseAccount(10.00, coinbaseAccount.currency, coinbaseAccount.id), 5.seconds)
    println(deposit)
    assert(deposit.isRight)
  }

  test("authenticatedClient.limitOrder sell should create a sell order") {
    val orderResponse: Either[ErrorCode, LimitOrderResponse] = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 4000.0, size = 2.0), 5.seconds)
    println(orderResponse)
    assert(orderResponse.isRight)
  }

  test("authenticatedClient.limitOrder sell should create a sell order with cancel 1 min") {
    val orderResponse: Either[ErrorCode, LimitOrderResponse] = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 5000.0, size = 2.0, cancelAfter = Some(CancelAfter.Min), timeInForce = Some(TimeInForce.GTT)), 5.seconds)
    println(orderResponse)
    assert(orderResponse.isRight)
  }

  test("authenticatedClient.limitOrder sell should create a sell order with cancel 1 hour") {
    val orderResponse: Either[ErrorCode, LimitOrderResponse] = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 6000.0, size = 2.0, cancelAfter = Some(CancelAfter.Hour), timeInForce = Some(TimeInForce.GTT)), 5.seconds)
    println(orderResponse)
    assert(orderResponse.isRight)
  }

  test("authenticatedClient.limitOrder sell should create a sell order with cancel 1 day") {
    val orderResponse: Either[ErrorCode, LimitOrderResponse] = Await.result(client.limitOrder("BTC-USD", Side.Sell, price = 7000.0, size = 2.0, cancelAfter = Some(CancelAfter.Day), timeInForce = Some(TimeInForce.GTT)), 5.seconds)
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