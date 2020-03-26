package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object NowSslApp extends App with NowService {
  implicit val system = ActorSystem.create("now", ConfigFactory.load("app.conf"))
  implicit val executor = system.dispatcher

  val sslContext = ConnectionContext.https(SSLContextFactory.newInstance(passphrase = "test"))
  val server = Http()
    .bindAndHandle(
      routes,
      "localhost",
      7777,
      connectionContext = sslContext
    )

  println(s"NowSsl app started at http://localhost:7777/\nPress RETURN to stop...")

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      println("NowSsl app stopped.")
    }
}