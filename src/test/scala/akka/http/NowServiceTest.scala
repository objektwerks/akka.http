package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.typesafe.config.ConfigFactory
import org.scalatest._

class NowServiceTest extends WordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll with NowService {
  val actorRefFactory = ActorSystem.create("now", ConfigFactory.load("test.conf"))
  val server = Http().bindAndHandle(routes, "localhost", 0)

  override protected def afterAll(): Unit = {
    server
      .flatMap(_.unbind)
      .onComplete(_ => system.terminate)
  }

  "NowService" should {
    "get and post" in {
      Get("/api/v1/now") ~> routes ~> check {
        status shouldBe StatusCodes.OK
      }
      Post("/api/v1/now", Now()) ~> routes ~> check {
        status shouldBe StatusCodes.OK
      }
    }
  }
}