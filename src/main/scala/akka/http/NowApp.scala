package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.io.StdIn

object NowApp extends App with NowService {
  val logger = LoggerFactory.getLogger(getClass)
  val conf = ConfigFactory.load("now.app.conf")
  implicit val system = ActorSystem.create(conf.getString("server.name"), conf)
  implicit val executor = system.dispatcher

  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  val server = Http()
    .bindAndHandle(
      routes,
      host,
      port
    )

  logger.info(s"*** NowApp started at http://$host:$port/\nPress RETURN to stop...")

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      logger.info("*** NowApp stopped.")
    }
}