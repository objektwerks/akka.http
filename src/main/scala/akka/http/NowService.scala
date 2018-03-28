package akka.http

import java.time.LocalTime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Now(time: String = LocalTime.now.toString)

trait NowService extends DefaultJsonProtocol with SprayJsonSupport {

  import akka.http.scaladsl.marshalling._
  import akka.http.scaladsl.server.Directives._

  implicit val nowFormat = jsonFormat1(Now)

  val api = path("now") {
    get {
      complete(ToResponseMarshallable[Now](Now()))
    }
  }
  val index = path("") {
    getFromResource("public/index.html")
  }
  val resources = get {
    getFromResourceDirectory("public")
  }
  val routes = api ~ index ~ resources
}