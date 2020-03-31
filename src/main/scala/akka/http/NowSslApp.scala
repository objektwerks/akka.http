package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

import scala.io.StdIn
import scala.util.{Failure, Success}

case class ServerConf(name: String, host: String, port: Int, service: String) {
  def tuple: (String, String, Int, String) = (name, host, port, service)
}

object NowSslApp extends App with NowService {
  val conf = ConfigFactory.load("now.ssl.app.conf")
  val (name, host, port, service) = conf.as[ServerConf]("server").tuple
  val sslContextConf = conf.as[SSLContextConf]("ssl")

  implicit val system = ActorSystem.create(name, conf)
  implicit val executor = system.dispatcher
  val logger = system.log

  val sslContext = SSLContextFactory.newInstance(sslContextConf)
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
  logger.info(s"*** SSL context conf: ${sslContextConf.toString}")
  logger.info(s"*** NowSslApp started at https://$host:$port/\nPress RETURN to stop...")

  val client = Http()
  client.setDefaultClientHttpsContext(httpsContext)
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