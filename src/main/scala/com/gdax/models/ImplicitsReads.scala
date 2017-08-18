package com.gdax.models

object ImplicitsReads {

  import com.gdax.models.GDaxProduct
  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._
  import play.api.libs.json._
  import java.time.Instant

  implicit val GDaxProductReads: Reads[GDaxProduct] = (
    (__ \ "id").read[String] and
      (__ \ "base_currency").read[String] and
      (__ \ "quote_currency").read[String] and
      (__ \ "base_min_size").read[String].map[Double](_.toDouble) and
      (__ \ "base_max_size").read[String].map[Double](_.toDouble) and
      (__ \ "quote_increment").read[String].map[Double](_.toDouble) and
      (__ \ "display_name").read[String] and
      (__ \ "margin_enabled").read[Boolean]
    ) (GDaxProduct.apply _)

  implicit val BookReads: Reads[Book] = (
    (__ \ "sequence").read[Long] and
      (__ \ "bids").read[JsArray].map[List[Bid]](_.value.map { innerArrayJson =>
        val innerArray = innerArrayJson.as[JsArray].value
        Bid(innerArray(0).as[String].toDouble, innerArray(1).as[String].toDouble, innerArray(2).as[Long])
      }.toList
      ) and
      (__ \ "asks").read[JsArray].map[List[Ask]](_.value.map { innerArrayJson =>
        val innerArray = innerArrayJson.as[JsArray].value
        Ask(innerArray(0).as[String].toDouble, innerArray(1).as[String].toDouble, innerArray(2).as[Long])
      }.toList
      )
    ) (Book.apply _)

  implicit val FullBookReads: Reads[FullBook] = (
    (__ \ "sequence").read[Long] and
      (__ \ "bids").read[JsArray].map[List[FullBid]](_.value.map { innerArrayJson =>
        val innerArray = innerArrayJson.as[JsArray].value
        FullBid(innerArray(0).as[String].toDouble, innerArray(1).as[String].toDouble, innerArray(2).as[String])
      }.toList
      ) and
      (__ \ "asks").read[JsArray].map[List[FullAsk]](_.value.map { innerArrayJson =>
        val innerArray = innerArrayJson.as[JsArray].value
        FullAsk(innerArray(0).as[String].toDouble, innerArray(1).as[String].toDouble, innerArray(2).as[String])
      }.toList
      )
    ) (FullBook.apply _)

  implicit val TimeRead: Reads[Time] = (
    (__ \ "iso").read[Instant] and
      (__ \ "epoch").read[Double]
    ) (Time.apply _)

  implicit val TickerReads: Reads[Ticker] = (
    (__ \ "trade_id").read[Long] and
      (__ \ "price").read[String].map[Double](_.toDouble) and
      (__ \ "size").read[String].map[Double](_.toDouble) and
      (__ \ "bid").read[String].map[Double](_.toDouble) and
      (__ \ "ask").read[String].map[Double](_.toDouble) and
      (__ \ "volume").read[String].map[Double](_.toDouble) and
      (__ \ "time").read[Instant]
    ) (Ticker.apply _)

  implicit val TradesReads: Reads[Trades] = (
    (__ \ "time").read[Instant] and
      (__ \ "trade_id").read[Long] and
      (__ \ "price").read[String].map[Double](_.toDouble) and
      (__ \ "size").read[String].map[Double](_.toDouble) and
      (__ \ "side").read[String]
    ) (Trades.apply _)

  implicit val CurrenciesRead: Reads[Currencies] = (
    (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "min_size").read[String].map[Double](_.toDouble)
    ) (Currencies.apply _)

  implicit val candleReads: Reads[Candle] =
    __.read[JsArray].map[Candle]((array: JsArray) => Candle(Instant.ofEpochSecond(array(0).as[Long]),
      array(1).as[Double], array(2).as[Double], array(3).as[Double],
      array(4).as[Double], array(5).as[Double]))

  implicit val DailyStatsReads: Reads[DailyStats] = (
    (__ \ "open").read[String].map[Double](_.toDouble) and
      (__ \ "high").read[String].map[Double](_.toDouble) and
      (__ \ "low").read[String].map[Double](_.toDouble) and
      (__ \ "volume").read[String].map[Double](_.toDouble)
    ) (DailyStats.apply _)

  implicit val AccountsReads: Reads[Accounts] = (
    (__ \ "id").read[String] and
      (__ \ "currency").read[String] and
      (__ \ "balance").read[String].map[Double](_.toDouble) and
      (__ \ "available").read[String].map[Double](_.toDouble) and
      (__ \ "hold").read[String].map[Double](_.toDouble) and
      (__ \ "profile_id").read[String]
    ) (Accounts.apply _)

  implicit val OneAccountRead: Reads[Account] = (
    (__ \ "id").read[String] and
      (__ \ "balance").read[String].map[Double](_.toDouble) and
      (__ \ "holds").read[String].map[Double](_.toDouble) and
      (__ \ "available").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String]
    ) (Account.apply _)

  implicit val OrderResponseReads: Reads[OrderResponse] = (
    (__ \ "id").read[String] and
      (__ \ "price").read[String].map[Double](_.toDouble) and
      (__ \ "size").read[String].map[Double](_.toDouble) and
      (__ \ "product_id").read[String] and
      (__ \ "side").read[String] and
      (__ \ "stp").read[String] and
      (__ \ "`type`").read[String] and
      (__ \ "time_in_force").read[String] and
      (__ \ "post_only").read[Boolean] and
      (__ \ "created_at").read[Instant] and
      (__ \ "fill_fees").read[String].map[Double](_.toDouble) and
      (__ \ "filled_size").read[String].map[Double](_.toDouble) and
      (__ \ "executed_value").read[String].map[Double](_.toDouble) and
      (__ \ "status").read[String] and
      (__ \ "settled").read[Boolean]
    ) (OrderResponse.apply _)

}
