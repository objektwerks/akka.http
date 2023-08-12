package akka.http

import java.time.LocalTime

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

case class Ping(now: String = LocalTime.now.toString)
object Ping {
  import upickle.default._

  implicit val pingRW: ReadWriter[Ping] = macroRW
}

// Can't use the low-level api to build rest url-driven services.
trait PingService {
  import upickle.default._

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/api/v1/ping"), _, _, _) =>
      HttpResponse(entity = HttpEntity(contentType = ContentTypes.`application/json`, string = write(Ping()).toString))
    case _: HttpRequest => HttpResponse(404, entity = "Unknown resource!")
  }
}

class LowLevelHttpTest extends AnyFunSuite with Matchers with BeforeAndAfterAll with PingService {
  import de.heikoseeberger.akkahttpupickle.UpickleSupport._

  implicit val system: ActorSystem = ActorSystem.create("ping", ConfigFactory.load("test.conf"))
  implicit val executor: ExecutionContext = system.dispatcher

  val server = Http()
    .newServerAt("localhost", 7777)
    .bindSync(requestHandler)

  override protected def afterAll(): Unit = {
    server.flatMap(_.unbind()).onComplete(_ => system.terminate())
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
    import scala.language.postfixOps
    val ping = Await.result(future, 3 seconds)
    println(s"get time ping: $ping")
  }
}