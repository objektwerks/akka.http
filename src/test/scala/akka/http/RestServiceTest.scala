package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.testkit.ScalatestRouteTest

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

trait RestService {
  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpupickle.UpickleSupport._

  case class User(id: String, name: String)
  object User {
    import upickle.default._

    implicit val userRW: ReadWriter[User] = macroRW
  }

  val getByUserId = path("users" / Segment) { id =>
    get {
      complete(OK -> User(id, "mike"))
    }
  }
  val routes = pathPrefix("api" / "v1" / "locator") {
    getByUserId
  }
}

class RestServiceTest extends AnyWordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll with RestService {
  import de.heikoseeberger.akkahttpupickle.UpickleSupport._

  val actorRefFactory = ActorSystem.create("user", ConfigFactory.load("test.conf"))
  val server = Http()
    .newServerAt("localhost", 0)
    .bindFlow(routes)

  override protected def afterAll(): Unit = {
    server
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  "getByUserId -> /users/{id}" should {
    "return user" in {
      Get("/api/v1/locator/users/a1") ~> routes ~> check {
        status shouldBe OK
        responseAs[User] shouldEqual User("a1", "mike")
      }
    }
  }
}