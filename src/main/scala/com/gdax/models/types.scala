package com.gdax.models

sealed trait GDaxResponse

/*
  "id": "BTC-USD",
  "base_currency": "BTC",
  "quote_currency": "USD",
  "base_min_size": "0.01",
  "base_max_size": "10000.00",
  "quote_increment": "0.01"
 */
case class GDaxProduct(id: String, base_currency: String, quote_currency: String, base_min_size: Double, base_max_size: Double, quote_increment: Double, display_name: String, margin_enabled: Boolean) extends GDaxResponse