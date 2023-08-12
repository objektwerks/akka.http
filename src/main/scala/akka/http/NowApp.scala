package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http

import com.typesafe.config.ConfigFactory

import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object NowApp extends App with NowService {
  val logger = LoggerFactory.getLogger(getClass)
  val conf = ConfigFactory.load("now.app.conf")
  implicit val system: ActorSystem = ActorSystem.create(conf.getString("server.name"), conf)
  implicit val executor: ExecutionContext = system.dispatcher

  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  val server = Http()
    .newServerAt(host, port)
    .bindFlow(routes)

  logger.info(s"*** NowApp started at http://$host:$port/\nPress RETURN to stop...")

  val line = StdIn.readLine()
  logger.info(s"*** NowApp stdin line: $line.")
  server
    .flatMap(_.unbind())
    .onComplete { _ =>
      system.terminate().foreach(terminated => logger.info(s"*** NowApp terminated? ${terminated.addressTerminated}"))
      logger.info("*** NowApp stopped.")
    }
}