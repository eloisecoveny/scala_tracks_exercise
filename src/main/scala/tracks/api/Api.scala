package tracks.api

import akka.http.scaladsl.server.Directives._
import org.json4s.{DefaultFormats, Formats, jackson}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import tracks.datasource.InMemoryDataStore
import tracks.models.Track

trait Api extends Json4sSupport {

  //refer to API trait in Boyzone (override lazy val playQueueDataSource) lets child apis use this source (HTTP datasource)

  // DB Client
  // Datasource - insert & get methods
  // API (uses datasource to do business logic)
  val dataStore = new InMemoryDataStore

  implicit val serialization = jackson.Serialization
  implicit val formats: Formats = DefaultFormats

  val routes =
  {
    path("status") {
      get {
        complete("OK")
      }
    } ~ path("tracks") {
      get {
        complete(dataStore.get)
      }
    } ~ path("tracks" / "add") {
      post {
        entity(as[Track]) { track =>
          dataStore.add(track)
          complete("OK")
        }
      }
    } ~ path("tracks" / Segment) { id =>
      delete {
        dataStore.remove(id)
        complete("OK")
      }
    } ~ path("tracks" / "update" / Segment) { id =>
      put {
        entity(as[Track]) { track =>
          dataStore.update(id, track)
          complete("OK")
        }
      }
    } ~ path("tracks" / Segment) { id =>
      get {
        complete(dataStore.getById(id))
      }
    }
  }
}
