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
  val keystorePath = conf.getString("server.keystore-path")
  val keystoreType = conf.getString("server.keystore-type")
  val sslContextProtocol = conf.getString("server.ssl-context-protocol")
  val algorithm = conf.getString("server.algorithm")
  val sslContextFactoryConf = SSLContextFactoryConf(passphrase, keystorePath, keystoreType, sslContextProtocol, algorithm)
  val sslContext = SSLContextFactory.newInstance(sslContextFactoryConf)
  val httpsContext = ConnectionContext.https(sslContext)
  val http = Http()
  http.setDefaultServerHttpContext(httpsContext)
  val server = http
    .bindAndHandle(
      routes,
      host,
      port,
      connectionContext = httpsContext
    )
  logger.info(s"*** NowSslApp started at https://$host:$port/\nPress RETURN to stop...")

  val client = Http()
  client.setDefaultClientHttpsContext(httpsContext)
  val service = conf.getString("server.service")
  client.singleRequest(HttpRequest(uri = s"https://$host:$port$service")).onComplete {
    case Success(response) => response.entity.dataBytes.map(_.utf8String).runForeach(json => logger.info(s"*** Now service: $json"))
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