name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.5"
libraryDependencies ++= {
  val akkaVersion = "2.4.14"
  val akkkHttpVersion = "10.0.0"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.12" % akkaVersion,
    "com.typesafe.akka" % "akka-slf4j_2.12" % akkaVersion,
    "com.typesafe.akka" % "akka-http_2.12" % akkkHttpVersion,
    "com.typesafe.akka" % "akka-http-spray-json_2.12" % akkkHttpVersion,
    "com.typesafe" % "config" % "1.3.1",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.akka" % "akka-http-testkit_2.12" % akkkHttpVersion % "test",
    "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test"
  )
}
scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-feature",
  "-Ywarn-unused-import",
  "-Ywarn-unused",
  "-Ywarn-dead-code",
  "-unchecked",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint:missing-interpolator",
  "-Xlint"
)
javaOptions += "-Xss1m -Xmx2g"