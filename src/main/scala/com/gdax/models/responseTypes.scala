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

case class OrderResponse(id: String, price: Double, size: Double, product_id: String, side: String,
                         stp: String, `type`: String, time_in_force: String, post_only: Boolean,
                         created_at: Instant, fill_fees: Double, filled_size: Double,
                         executed_value: Double, status: String, settled: Boolean)