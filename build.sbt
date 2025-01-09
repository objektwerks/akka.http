name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.15"
libraryDependencies ++= {
  val akkaVersion = "2.6.21" // Don't upgrade due to BUSL 1.1!
  val akkkHttpVersion = "10.2.10" // Don't upgrade due to BUSL 1.1!
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.39.2",
    "com.lihaoyi" %% "upickle" % "4.1.0",
    "com.typesafe" % "config" % "1.4.3",
    "com.iheart" %% "ficus" % "1.5.2",
    "ch.qos.logback" % "logback-classic" % "1.5.16",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
