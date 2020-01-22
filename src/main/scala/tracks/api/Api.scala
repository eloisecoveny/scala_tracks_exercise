package tracks.api

import akka.http.scaladsl.server.Directives._

trait Api {

  val routes =
  {
    path("hello") {
      get {
        complete("Hello, World!")
      }
    } ~ path("tracks") {
      get {
        complete("tracks")
      }
    }
  }
}
