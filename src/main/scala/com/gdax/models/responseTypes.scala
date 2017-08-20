package com.gdax.models

import java.time.Instant


case class GDaxProduct(id: String, base_currency: String, quote_currency: String, base_min_size: Double, base_max_size: Double, quote_increment: Double, display_name: String, margin_enabled: Boolean)

case class Bid(price: Double, size: Double, numOrders: Long)

case class Ask(price: Double, size: Double, numOrders: Long)

case class Book(sequence: Long, bids: List[Bid], asks: List[Ask])

case class Details(order_id: String, trade_id: String, product_id: String)

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

case class MarketAndStopOrderResponse(id: String, size: Double, product_id: String, side: String,
                                      stp: String, `type`: String, post_only: Boolean,
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

case class AccountHistory(id: String, created_at: Instant, amount: Double, balance: Double, `type`: String,
                          details: Option[Details])
/*
[
    {
        "id": "fc3a8a57-7142-542d-8436-95a3d82e1622",
        "name": "ETH Wallet",
        "balance": "0.00000000",
        "currency": "ETH",
        "type": "wallet",
        "primary": false,
        "active": true
    },
    {
        "id": "2ae3354e-f1c3-5771-8a37-6228e9d239db",
        "name": "USD Wallet",
        "balance": "0.00",
        "currency": "USD",
        "type": "fiat",
        "primary": false,
        "active": true,
        "wire_deposit_information": {
            "account_number": "0199003122",
            "routing_number": "026013356",
            "bank_name": "Metropolitan Commercial Bank",
            "bank_address": "99 Park Ave 4th Fl New York, NY 10016",
            "bank_country": {
                "code": "US",
                "name": "United States"
            },
            "account_name": "Coinbase, Inc",
            "account_address": "548 Market Street, #23008, San Francisco, CA 94104",
            "reference": "BAOCAEUX"
        }
    },
    {
        "id": "1bfad868-5223-5d3c-8a22-b5ed371e55cb",
        "name": "BTC Wallet",
        "balance": "0.00000000",
        "currency": "BTC",
        "type": "wallet",
        "primary": true,
        "active": true
    },
    {
        "id": "2a11354e-f133-5771-8a37-622be9b239db",
        "name": "EUR Wallet",
        "balance": "0.00",
        "currency": "EUR",
        "type": "fiat",
        "primary": false,
        "active": true,
        "sepa_deposit_information": {
            "iban": "EE957700771001355096",
            "swift": "LHVBEE22",
            "bank_name": "AS LHV Pank",
            "bank_address": "Tartu mnt 2, 10145 Tallinn, Estonia",
            "bank_country_name": "Estonia",
            "account_name": "Coinbase UK, Ltd.",
            "account_address": "9th Floor, 107 Cheapside, London, EC2V 6DN, United Kingdom",
            "reference": "CBAEUXOVFXOXYX"
        }
    },
]
 */