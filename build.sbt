name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.11"
libraryDependencies ++= {
  val akkaVersion = "2.6.5"
  val akkkHttpVersion = "10.1.12"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.32.0",
    "com.lihaoyi" %% "upickle" % "1.1.0",
    "com.typesafe" % "config" % "1.4.0",
    "com.iheart" %% "ficus" % "1.4.7",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.1.2" % Test
  )
}