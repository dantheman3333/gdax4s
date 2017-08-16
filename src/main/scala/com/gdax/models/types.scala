package com.gdax.models

import java.sql.Timestamp


/*
  {
  "id": "LTC-EUR",
  "base_currency": "LTC",
  "quote_currency": "EUR",
  "base_min_size": "0.01",
  "base_max_size": "1000000",
  "quote_increment": "0.01",
  "display_name": "LTC/EUR",
  "margin_enabled": false
  },
 */
case class GDaxProduct(id: String, base_currency: String, quote_currency: String, base_min_size: Double, base_max_size: Double, quote_increment: Double, display_name: String, margin_enabled: Boolean)

/*
{
  "sequence": 3848206325,
  "bids": [
    [
      "4183.08",
      "27.24436344",
      5
    ]
  ],
  "asks": [
    [
      "4183.09",
      "4.59708523",
      5
    ]
  ]
}
 */
case class Bid(price: Double, size: Double, numOrders: Long)
case class Ask(price: Double, size: Double, numOrders: Long)
case class Book(sequence: Long, bids: List[Bid], asks: List[Ask])

case class FullBid(price: Double, size: Double, order_id : String)
case class FullAsk(price: Double, size: Double, order_id : String)
case class FullBook(sequence: Long, bids: List[FullBid], asks: List[FullAsk])

case class Time(iso: Timestamp, epoch: Double)