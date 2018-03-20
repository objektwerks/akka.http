package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory

class NowApp extends NowService {
  val actorRefFactory = ActorSystem.create("now", ConfigFactory.load("app.conf"))
  val server = Http().bindAndHandle(routes, "localhost", 0)
}