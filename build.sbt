name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.6"
libraryDependencies ++= {
  val akkaVersion = "2.6.15"
  val akkkHttpVersion = "10.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.37.0",
    "com.lihaoyi" %% "upickle" % "1.4.0",
    "com.typesafe" % "config" % "1.4.1",
    "com.iheart" %% "ficus" % "1.5.0",
    "ch.qos.logback" % "logback-classic" % "1.2.5",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.9" % Test
  )
}
