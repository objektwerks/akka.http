package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn
import scala.util.{Failure, Success}

object NowSslApp extends App with NowService {
  val conf = ConfigFactory.load("now.ssl.app.conf")
  implicit val system = ActorSystem.create(conf.getString("server.name"), conf)
  implicit val executor = system.dispatcher
  val logger = system.log

  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  val passphrase = conf.getString("server.passphrase")
  val sslContext = SSLContextFactory.newInstance(passphrase = passphrase)
  val httpsContext = ConnectionContext.https(sslContext)
  val http = Http()
  http.setDefaultClientHttpsContext(httpsContext)
  val server = http
    .bindAndHandle(
      routes,
      host,
      port,
      connectionContext = httpsContext
    )
  logger.info(s"*** NowSslApp started at https://$host:$port/\nPress RETURN to stop...")

  val future = http.singleRequest(HttpRequest(uri = s"https://$host:$port/api/v1/now"))
  future
    .onComplete {
      case Success(now) => logger.info(s"*** The current now is: $now")
      case Failure(error) => logger.error(s"*** Now service failed: ${error.toString}")
    }

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      logger.info("*** NowSslApp stopped.")
    }
}