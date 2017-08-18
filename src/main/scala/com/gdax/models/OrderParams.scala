package com.gdax.models

object OrderParams {

  trait LowerCaseToString extends Product{
    override def toString: String = this.productPrefix.toLowerCase
  }

  object TimeInForce {
    //GTC, GTT, IOC, or FOK (default is GTC)
    sealed trait TimeInForce
    case object GTC extends TimeInForce
    case object GTT extends TimeInForce
    case object IOC extends TimeInForce
    case object FOK extends TimeInForce
  }

  object Side {
    sealed trait Side extends LowerCaseToString
    case object Buy extends Side
    case object Sell extends Side
  }

  object CancelAfter{
    sealed trait CancelAfter extends LowerCaseToString
    case object Min extends CancelAfter
    case object Hour extends CancelAfter
    case object Day extends CancelAfter
  }

  object OrderType{
    sealed trait OrderType extends LowerCaseToString
    case object Limit extends OrderType
    case object Market extends OrderType
    case object Stop extends OrderType
  }
}
