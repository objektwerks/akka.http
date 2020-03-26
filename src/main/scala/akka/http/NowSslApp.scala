package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.io.StdIn

object NowSslApp extends App with NowService {
  val logger = LoggerFactory.getLogger(getClass)
  val conf = ConfigFactory.load("now.ssl.app.conf")
  implicit val system = ActorSystem.create(conf.getString("server.name"), conf)
  implicit val executor = system.dispatcher

  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  val passphrase = conf.getString("server.passphrase")
  val sslContext = ConnectionContext.https(SSLContextFactory.newInstance(passphrase = passphrase))
  val server = Http()
    .bindAndHandle(
      routes,
      host,
      port,
      connectionContext = sslContext
    )

  logger.info(s"*** NowSslApp started at https://$host:$port/\nPress RETURN to stop...")

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      logger.info("*** NowSslApp stopped.")
    }
}