package akka.http

import java.time.LocalTime

import akka.http.scaladsl.model.StatusCodes.OK

case class Now(time: String = s"*** The current local time is: ${LocalTime.now.toString}")

object Now {
  import upickle.default._

  implicit val nowRW: ReadWriter[Now] = macroRW
}

trait NowService {
  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpupickle.UpickleSupport._

  val getNow = get {
    complete(OK -> Now())
  }
  val postNow = post {
    entity(as[Now]) { _ => complete(OK) }
  }
  val api = pathPrefix("api" / "v1" / "now") {
    getNow ~ postNow
  }
  val index = path("") {
    getFromResource("public/index.html")
  }
  val resources = get {
    getFromResourceDirectory("public")
  }
  val routes = api ~ index ~ resources
}