package akka.http

import java.time.LocalTime

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}
import spray.json.DefaultJsonProtocol

import scala.util.{Failure, Success}

case class Ping(now: String = LocalTime.now.toString)

trait PingService extends DefaultJsonProtocol with SprayJsonSupport {
  import spray.json._

  implicit val pingFormat = jsonFormat1(Ping)

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/api/v1/ping"), _, _, _) =>
      HttpResponse(entity = HttpEntity(Ping().toJson.toString()))

    case _: HttpRequest => HttpResponse(404, entity = "Unknown resource!")
  }
}

class LowLevelHttpTest extends FunSuite with Matchers with BeforeAndAfterAll with PingService {
  implicit val system = ActorSystem.create("ping", ConfigFactory.load("app.conf"))
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher

  val server = Http().bindAndHandleSync(requestHandler, "localhost", 7777)

  override protected def afterAll(): Unit = {
    server.flatMap(_.unbind()).onComplete(_ ⇒ system.terminate())
  }

  test("get time") {
    val httpResponse = Http().singleRequest(HttpRequest(uri = "http://api/v1/ping"))
    httpResponse.onComplete {
      case Success(response) => println(response); response shouldBe Ping
      case Failure(error) => sys.error(error.getMessage)
    }
  }
}