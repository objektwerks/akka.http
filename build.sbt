name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.12"
libraryDependencies ++= {
  val akkaVersion = "2.6.21" // Don't upgrade due to BUSL 1.1!
  val akkkHttpVersion = "10.2.10" // Don't upgrade due to BUSL 1.1!
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.39.2",
    "com.lihaoyi" %% "upickle" % "1.4.3", // Don't edit! akka-http-upickle depends on this version!
    "com.typesafe" % "config" % "1.4.2",
    "com.iheart" %% "ficus" % "1.5.2",
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.17" % Test
  )
}
