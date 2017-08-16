package com.gdax.models

import java.time.Instant


case class GDaxProduct(id: String, base_currency: String, quote_currency: String, base_min_size: Double, base_max_size: Double, quote_increment: Double, display_name: String, margin_enabled: Boolean)

case class Bid(price: Double, size: Double, numOrders: Long)
case class Ask(price: Double, size: Double, numOrders: Long)
case class Book(sequence: Long, bids: List[Bid], asks: List[Ask])

case class FullBid(price: Double, size: Double, order_id : String)
case class FullAsk(price: Double, size: Double, order_id : String)
case class FullBook(sequence: Long, bids: List[FullBid], asks: List[FullAsk])

case class Time(iso: Instant, epoch: Double)

case class Ticker(trade_id: Long, price: Double, size: Double, bid: Double, ask: Double, volume: Double, time: Instant)

case class Trades(time: Instant, trade_id: Long, price: Double, size: Double, side: String)

case class Currencies(min_size: Double, id: String, name: String)