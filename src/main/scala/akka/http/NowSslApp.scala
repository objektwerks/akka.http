package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

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

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      logger.info("*** NowSslApp stopped.")
    }
}