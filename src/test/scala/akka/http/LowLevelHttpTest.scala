package akka.http

import java.time.LocalTime

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}
import spray.json.DefaultJsonProtocol

import scala.concurrent.Await
import scala.concurrent.duration._

case class Ping(now: String = LocalTime.now.toString)

trait PingService extends DefaultJsonProtocol with SprayJsonSupport {
  import spray.json._

  implicit val pingFormat = jsonFormat1(Ping)

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/api/v1/ping"), _, _, _) =>
      HttpResponse(entity = HttpEntity(contentType = ContentTypes.`application/json`, string = Ping().toJson.toString))
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
    val request = RequestBuilding.Get("http://localhost:7777/api/v1/ping")
    val future = for {
      response <- Http().singleRequest(request)
      entity <- Unmarshal(response.entity).to[Ping]
    } yield {
      println(s"get time response: $response")
      entity
    }
    val ping = Await.result(future, 3 seconds)
    println(s"get time ping: $ping")
  }
}