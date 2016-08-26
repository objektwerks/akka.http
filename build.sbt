name := "akka.http"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.11.8"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
libraryDependencies ++= {
  val akkaVersion = "2.4.9"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % akkaVersion,
    "com.typesafe.akka" % "akka-slf4j_2.11" % akkaVersion,
    "com.typesafe.akka" % "akka-http-experimental_2.11" % akkaVersion,
    "com.typesafe.akka" % "akka-http-spray-json-experimental_2.11" % akkaVersion,
    "com.typesafe" % "config" % "1.3.0",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" % "akka-http-testkit-experimental_2.11" % "2.4.2-RC3" % "test",
    "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test"
  )
}
scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:reflectiveCalls",
  "-language:higherKinds",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Xfatal-warnings"
)
javaOptions += "-Xss1m -Xmx2g"
fork in test := true