package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object NowApp extends NowService with App {
  implicit val system = ActorSystem.create("now", ConfigFactory.load("web/app.conf"))
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val server = Http().bindAndHandle(routes, "localhost", 7777)

  sys.addShutdownHook {
    server.flatMap(_.unbind()).onComplete(_ ⇒ system.terminate())
  }
}