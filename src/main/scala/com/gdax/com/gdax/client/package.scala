package com.gdax.com.gdax


package object client {

  import play.api.libs.json._
  import play.api.libs.json.Reads._
  import play.api.libs.functional.syntax._


  import com.gdax.models.GDaxProduct

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

}
