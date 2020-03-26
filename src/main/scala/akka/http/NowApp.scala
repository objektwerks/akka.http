package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object NowApp extends App with NowService {
  val conf = ConfigFactory.load("now.app.conf")
  implicit val system = ActorSystem.create(conf.getString("server.name"), conf.getConfig("akka"))
  implicit val executor = system.dispatcher

  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  val server = Http()
    .bindAndHandle(
      routes,
      host,
      port
    )

  println(s"NowApp started at http://$host:$port/\nPress RETURN to stop...")

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      println("NowApp stopped.")
    }
}