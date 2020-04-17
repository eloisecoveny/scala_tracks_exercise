package tracks.api

import akka.http.scaladsl.server.Directives._

trait Api {

  //refer to API trait in Boyzone (override lazy val playQueueDataSource) lets child apis use this source (HTTP datasource)

  // DB Client
  // Datasource - insert & get methods
  // API (uses datasource to do business logic)

  val routes =
  {
    path("status") {
      get {
        complete("OK")
      }
    } ~ path("tracks") {
      get {
        complete("tracks")
      }
    }
  }
}
