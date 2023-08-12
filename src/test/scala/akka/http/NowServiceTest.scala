package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class NowServiceTest extends AnyWordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll with NowService {
  import de.heikoseeberger.akkahttpupickle.UpickleSupport._

  val actorRefFactory = ActorSystem.create("now", ConfigFactory.load("test.conf"))
  val server = Http()
    .newServerAt("localhost", 0)
    .bindFlow(routes)

  override protected def afterAll(): Unit = {
    server
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  "NowService" should {
    "get and post" in {
      Get("/api/v1/now") ~> routes ~> check {
        val assertion = status shouldBe StatusCodes.OK
        println(s"*** Now Service get and post assertion: ${assertion.toString}")
      }
      Post("/api/v1/now", Now()) ~> routes ~> check {
        status shouldBe StatusCodes.OK
      }
    }
  }
}