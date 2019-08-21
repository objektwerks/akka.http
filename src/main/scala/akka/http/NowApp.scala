package akka.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object NowApp extends App with NowService {
  implicit val system = ActorSystem.create("now", ConfigFactory.load("app.conf"))
  implicit val materializer = ActorMaterializer()
  implicit val executor = system.dispatcher

  val server = Http().bindAndHandle(routes, "localhost", 7777)
  println(s"Now app started at http://localhost:7777/\nPress RETURN to stop...")

  StdIn.readLine()
  server
    .flatMap(_.unbind)
    .onComplete { _ =>
      system.terminate
      println("Now app stopped.")
    }
}