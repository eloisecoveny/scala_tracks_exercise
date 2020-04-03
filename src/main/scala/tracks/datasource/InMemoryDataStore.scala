package tracks.datasource

import tracks.models.Track

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class InMemoryDataStore extends DataStore {
  import scala.concurrent.ExecutionContext.Implicits.global

  val datastore: mutable.ListBuffer[Track] = new ListBuffer[Track]

  def length = datastore.length
  def add(track: Track) = datastore += track
  def remove(id: String) = {
    datastore
      .toList
      .find(track => track.id == id)
      .map(track => datastore -= track)
  }
  def update(id: String, track: Track) = {
    datastore
      .toList
      .find(track => track.id == id)
      .map(foundTrack => {
        remove(foundTrack.id)
        add(track)
      })
  }
  def get: Future[List[Track]] = {
    Future{datastore.toList}
  }
  def getById(id: String): Future[Option[Track]] = {
    Future {
      datastore
        .toList
        .find(track => track.id == id)
    }
  }
}
