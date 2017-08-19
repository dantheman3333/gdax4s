package com.gdax.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

object ImplicitWrites {
  implicit val DepositFromPaymentMethodPostWrites: Writes[DepositFromPaymentMethodPost] = (
    (__ \ "amount").write[Double] and
      (__ \ "currency").write[String] and
      (__ \ "payment_method_id").write[String]
    )(unlift(DepositFromPaymentMethodPost.unapply))
}