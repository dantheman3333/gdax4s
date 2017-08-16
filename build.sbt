name := "gdax4s"

version := "1.0"

scalaVersion := "2.12.3"

val playWsStandaloneVersion = "1.0.4"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % playWsStandaloneVersion
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % playWsStandaloneVersion

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"