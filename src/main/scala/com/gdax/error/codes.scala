package com.gdax.error

sealed trait ErrorCode
case class RequestError(statusCode: Int) extends ErrorCode
case class InvalidJson(jsonError: String, reason: Option[Throwable] = None) extends ErrorCode

/*
https://docs.gdax.com/?python#errors
400	Bad Request – Invalid request format
401	Unauthorized – Invalid API Key
403	Forbidden – You do not have access to the requested resource
404	Not Found
429 Too Many Requests
500	Internal Server Error – We had a problem with our server
 */