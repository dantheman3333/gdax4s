package com.gdax.client

import com.gdax.models.OrderParams.{CancelAfter, OrderType, Side, TimeInForce}
import org.scalatest.FunSuite

class OrderParamsSuite extends FunSuite {
  test("TimeInForce.class.toString returns correct values in uppercase"){
    assert(TimeInForce.GTC.toString == "GTC")
    assert(TimeInForce.GTT.toString == "GTT")
    assert(TimeInForce.IOC.toString == "IOC")
    assert(TimeInForce.FOK.toString == "FOK")
  }

  test("Side.class.toString returns correct values in lowercase"){
    assert(Side.Buy.toString == "buy")
    assert(Side.Sell.toString == "sell")
  }

  test("CancelAfter.class.toString returns correct values in lowercase"){
    assert(CancelAfter.Min.toString == "min")
    assert(CancelAfter.Hour.toString == "hour")
    assert(CancelAfter.Day.toString == "day")
  }

  test("OrderType.class.toString returns correct values in lowercase"){
    assert(OrderType.Limit.toString == "limit")
    assert(OrderType.Market.toString == "market")
    assert(OrderType.Stop.toString == "stop")
  }
}
