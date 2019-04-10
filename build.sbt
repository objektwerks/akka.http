name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.8"
libraryDependencies ++= {
  val akkaVersion = "2.5.22"
  val akkkHttpVersion = "10.1.8"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.12" % akkaVersion,
    "com.typesafe.akka" % "akka-slf4j_2.12" % akkaVersion,
    "com.typesafe.akka" % "akka-http_2.12" % akkkHttpVersion,
    "com.typesafe.akka" % "akka-http-spray-json_2.12" % akkkHttpVersion,
    "com.typesafe.akka" % "akka-stream_2.12" % akkaVersion,
    "com.typesafe" % "config" % "1.3.3",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.akka" % "akka-testkit_2.12" % akkaVersion % "test",
    "com.typesafe.akka" % "akka-http-testkit_2.12" % akkkHttpVersion % Test,
    "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test
  )
}