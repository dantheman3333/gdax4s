package com.gdax.models

object ImplicitsReads {

  import java.time.Instant

  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._
  import play.api.libs.json._

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

  implicit val AccountsReads: Reads[AccountWithProfile] = (
    (__ \ "id").read[String] and
      (__ \ "currency").read[String] and
      (__ \ "balance").read[String].map[Double](_.toDouble) and
      (__ \ "available").read[String].map[Double](_.toDouble) and
      (__ \ "hold").read[String].map[Double](_.toDouble) and
      (__ \ "profile_id").read[String]
    ) (AccountWithProfile.apply _)

  implicit val OneAccountRead: Reads[Account] = (
    (__ \ "id").read[String] and
      (__ \ "balance").read[String].map[Double](_.toDouble) and
      (__ \ "hold").read[String].map[Double](_.toDouble) and
      (__ \ "available").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String]
    ) (Account.apply _)

  implicit val OrderResponseReads: Reads[LimitOrderResponse] = (
    (__ \ "id").read[String] and
      (__ \ "price").read[String].map[Double](_.toDouble) and
      (__ \ "size").read[String].map[Double](_.toDouble) and
      (__ \ "product_id").read[String] and
      (__ \ "side").read[String] and
      (__ \ "stp").read[String] and
      (__ \ "type").read[String] and
      (__ \ "time_in_force").read[String] and
      (__ \ "post_only").read[Boolean] and
      (__ \ "created_at").read[Instant] and
      (__ \ "fill_fees").read[String].map[Double](_.toDouble) and
      (__ \ "filled_size").read[String].map[Double](_.toDouble) and
      (__ \ "executed_value").read[String].map[Double](_.toDouble) and
      (__ \ "status").read[String] and
      (__ \ "settled").read[Boolean]
    ) (LimitOrderResponse.apply _)


  implicit val MarketAndStopOrderResponseReads: Reads[MarketAndStopOrderResponse] = (
    (__ \ "id").read[String] and
      (__ \ "size").readNullable[String].map[Option[Double]](_.map(_.toDouble)) and
      (__ \ "product_id").read[String] and
      (__ \ "side").read[String] and
      (__ \ "stp").read[String] and
      (__ \ "funds").readNullable[String].map[Option[Double]](_.map(_.toDouble)) and
      (__ \ "specified_funds").readNullable[String].map[Option[Double]](_.map(_.toDouble)) and
      (__ \ "type").read[String] and
      (__ \ "post_only").read[Boolean] and
      (__ \ "created_at").read[Instant] and
      (__ \ "fill_fees").read[String].map[Double](_.toDouble) and
      (__ \ "filled_size").read[String].map[Double](_.toDouble) and
      (__ \ "executed_value").read[String].map[Double](_.toDouble) and
      (__ \ "status").read[String] and
      (__ \ "settled").read[Boolean]
    ) (MarketAndStopOrderResponse.apply _)

  implicit val CoinBaseDepositRead: Reads[CoinBaseDeposit] = (
    (__ \ "id").read[String] and
      (__ \ "amount").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String]
    ) (CoinBaseDeposit.apply _)

  implicit val PaymentMethodDepositRead: Reads[PaymentMethodDeposit] = (
    (__ \ "id").read[String] and
      (__ \ "amount").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String] and
      (__ \ "payout_at").read[Instant]
    ) (PaymentMethodDeposit.apply _)

  implicit val LimitTotalRead: Reads[LimitTotal] = (
    (__ \ "amount").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String]
    ) (LimitTotal.apply _)

  implicit val LimitRemainingRead: Reads[LimitRemaining] = (
    (__ \ "amount").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String]
    ) (LimitRemaining.apply _)

  implicit val LimitRead: Reads[Limit] = (
    (__ \ "period_in_days").read[Long] and
      (__ \ "total").read[LimitTotal] and
      (__ \ "remaining").read[LimitRemaining]
    ) (Limit.apply _)

  implicit val LimitsRead: Reads[Limits] = (
    (__ \ "buy").readNullable[List[Limit]] and
      (__ \ "instant_buy").readNullable[List[Limit]] and
      (__ \ "sell").readNullable[List[Limit]] and
      (__ \ "deposit").readNullable[List[Limit]]
    ) (Limits.apply _)

  implicit val FiatAccountRead: Reads[FiatAccount] = (
    (__ \ "id").read[String] and
      (__ \ "resource").read[String]
    ) (FiatAccount.apply _)

  implicit val PaymentMethodRead: Reads[PaymentMethod] = (
    (__ \ "id").read[String] and
      (__ \ "type").read[String] and
      (__ \ "verified").readNullable[Boolean] and
      (__ \ "verification_method").readNullable[String] and
      (__ \ "cdv_status").readNullable[String] and
      (__ \ "name").read[String] and
      (__ \ "currency").read[String] and
      (__ \ "primary_buy").read[Boolean] and
      (__ \ "primary_sell").read[Boolean] and
      (__ \ "allow_buy").read[Boolean] and
      (__ \ "allow_sell").read[Boolean] and
      (__ \ "allow_deposit").read[Boolean] and
      (__ \ "allow_withdraw").read[Boolean] and
      (__ \ "created_at").read[Instant] and
      (__ \ "updated_at").read[Instant] and
      (__ \ "resource").read[String] and
      (__ \ "resource_path").read[String] and
      (__ \ "limits").read[Limits] and
      (__ \ "fiat_account").readNullable[FiatAccount]
    ) (PaymentMethod.apply _)


  implicit val BankCountryRead: Reads[BankCountry] = (
    (__ \ "code").read[String] and
      (__ \ "name").read[String]
    ) (BankCountry.apply _)

  implicit val WireDepositInformationRead: Reads[WireDepositInformation] = (
    (__ \ "account_number").read[String] and
      (__ \ "routing_number").read[String] and
      (__ \ "bank_name").read[String] and
      (__ \ "bank_address").read[String] and
      (__ \ "bank_country").read[BankCountry] and
      (__ \ "account_name").read[String] and
      (__ \ "account_address").read[String] and
      (__ \ "reference").read[String]
    ) (WireDepositInformation.apply _)

  implicit val SepaDepositInformationRead: Reads[SepaDepositInformation] = (
    (__ \ "iban").read[String] and
      (__ \ "swift").read[String] and
      (__ \ "bank_name").read[String] and
      (__ \ "bank_address").read[String] and
      (__ \ "account_name").read[String] and
      (__ \ "account_address").read[String] and
      (__ \ "reference").read[String]
    ) (SepaDepositInformation.apply _)

  implicit val CanceledOrdersRead: Reads[CanceledOrders] = (
    __.read[JsArray].map[List[String]](_.as[List[String]])
    ).map(CanceledOrders(_))

  implicit val CoinBaseAccountRead: Reads[CoinBaseAccount] = (
    (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "balance").read[String].map[Double](_.toDouble) and
      (__ \ "currency").read[String] and
      (__ \ "primary").read[Boolean] and
      (__ \ "active").read[Boolean] and
      (__ \ "wire_deposit_information").readNullable[WireDepositInformation] and
        (__ \ "sepa_deposit_information").readNullable[SepaDepositInformation]
    ) (CoinBaseAccount.apply _)

  implicit val DetailsRead: Reads[Details] = (
    (__ \ "order_id").read[String] and
      (__ \ "trade_id").read[String] and
      (__ \ "product_id").read[String]
    )(Details.apply _)

  implicit val AccountHistoryReads: Reads[AccountHistory] = (
    (__ \ "id").read[String] and
      (__ \ "created_at").read[Instant] and
      (__ \ "amount").read[String].map[Double](_.toDouble) and
      (__ \ "balance").read[String].map[Double](_.toDouble) and
      (__ \ "type").read[String] and
      (__ \ "details").readNullable[Details]
    ) (AccountHistory.apply _)

}