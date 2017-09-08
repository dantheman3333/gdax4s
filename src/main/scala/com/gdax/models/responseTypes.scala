package com.gdax.models

import java.time.Instant


case class GDaxProduct(id: String, base_currency: String, quote_currency: String, base_min_size: Double, base_max_size: Double, quote_increment: Double, display_name: String, margin_enabled: Boolean)

case class Bid(price: Double, size: Double, numOrders: Long)

case class Ask(price: Double, size: Double, numOrders: Long)

case class Book(sequence: Long, bids: List[Bid], asks: List[Ask])

case class FullBid(price: Double, size: Double, order_id: String)

case class FullAsk(price: Double, size: Double, order_id: String)

case class FullBook(sequence: Long, bids: List[FullBid], asks: List[FullAsk])

case class Time(iso: Instant, epoch: Double)

case class Ticker(trade_id: Long, price: Double, size: Double, bid: Double, ask: Double, volume: Double, time: Instant)

case class Trades(time: Instant, trade_id: Long, price: Double, size: Double, side: String)

case class Currencies(id: String, name: String, min_size: Double)

case class Candle(time: Instant, low: Double, high: Double, open: Double, close: Double, volume: Double)

case class DailyStats(open: Double, high: Double, low: Double, volume: Double)

case class LimitOrderResponse(id: String, price: Double, size: Double, product_id: String, side: String,
                              stp: String, `type`: String, time_in_force: String, post_only: Boolean,
                              created_at: Instant, fill_fees: Double, filled_size: Double,
                              executed_value: Double, status: String, settled: Boolean)

case class MarketAndStopOrderResponse(id: String, size: Option[Double], product_id: String, side: String,
                                      stp: String, funds: Option[Double], specified_funds: Option[Double], `type`: String, post_only: Boolean,
                                      created_at: Instant, fill_fees: Double, filled_size: Double,
                                      executed_value: Double, status: String, settled: Boolean)

case class AccountWithProfile(id: String, currency: String, balance: Double, available: Double, hold: Double, profile_id: String)

case class Account(id: String, balance: Double, hold: Double, available: Double, currency: String)

case class CoinBaseDeposit(id: String, amount: Double, currency: String)

case class PaymentMethodDeposit(id: String, amount: Double, currency: String, payout_at: Instant)

case class LimitTotal(amount: Double, currency: String)
case class LimitRemaining(amount: Double, currency: String)
case class Limit(period_in_days: Long, total: LimitTotal, remaining: LimitRemaining)

case class Limits(buy: Option[List[Limit]], instant_buy: Option[List[Limit]], sell: Option[List[Limit]], deposit: Option[List[Limit]])

case class FiatAccount(id: String, resource: String)

case class PaymentMethod(id: String, `type`: String, verified: Option[Boolean], verification_method: Option[String],
                         cdv_status: Option[String], name: String, currency: String, primary_buy: Boolean,
                         primary_sell: Boolean, allow_buy: Boolean, allow_sell: Boolean, allow_deposit: Boolean,
                         allow_withdraw: Boolean, created_at: Instant, updated_at: Instant, resource: String,
                         resource_path: String, limits: Limits, fiat_account: Option[FiatAccount])


case class BankCountry(code: String, name: String)

case class WireDepositInformation(account_number: String, routing_number: String, bank_name: String,
                                  bank_address: String, bank_country: BankCountry, account_name: String,
                                  account_address: String, reference: String)

case class SepaDepositInformation(iban: String, swift: String, bank_name: String, bank_address: String,
                                  account_name: String, account_address: String, reference: String)

case class CoinBaseAccount(id: String, name: String, balance: Double, currency: String, primary: Boolean,
                           active: Boolean, wire_deposit_information: Option[WireDepositInformation],
                           sepa_deposit_information: Option[SepaDepositInformation])

case class Details(order_id: String, trade_id: String, product_id: String)

case class AccountHistory(id: String, created_at: Instant, amount: Double, balance: Double, `type`: String,
                          details: Option[Details])

case class CanceledOrders(ids: List[String])