package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import spray.json.DefaultJsonProtocol

trait RestService extends DefaultJsonProtocol with SprayJsonSupport {
  import akka.http.scaladsl.marshalling._
  import akka.http.scaladsl.server.Directives._

  case class User(id: String, name: String)
  implicit val userFormat = jsonFormat2(User)

  val getByUserId = path("users" / """id(\[a-zA-Z0-9])""".r) { id =>
    complete(ToResponseMarshallable[User](User(id, "mike")))
  }
  val routes = pathPrefix("api" / "v1" / "locator") {
    getByUserId
  }
}

class RestServiceTest extends WordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll with RestService {
  val actorRefFactory = ActorSystem.create("user", ConfigFactory.load("test.conf"))
  val server = Http().bindAndHandle(routes, "localhost", 0)

  override protected def afterAll(): Unit = {
    server.flatMap(_.unbind()).onComplete(_ ⇒ system.terminate())
  }

  "getByUserId -> /users/{id}" should {
    "return user" in {
      Get("/api/v1/locator/users/a1") ~> routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[User] shouldEqual User("a1", "mike")
      }
    }
  }
}