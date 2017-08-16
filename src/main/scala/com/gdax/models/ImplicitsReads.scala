package com.gdax.models

object ImplicitsReads {

  import com.gdax.models.GDaxProduct
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
}
