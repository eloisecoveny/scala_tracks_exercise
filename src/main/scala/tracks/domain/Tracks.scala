package tracks.domain

import tracks.datasource.DataStore
import tracks.models.Track

import scala.concurrent.Future

class Tracks(data: DataStore) {

  def add(track: Track): Unit = {
    data.add(track)
  }

  def remove(id: String): Unit = {
    data.remove(id)
  }

  def update(id: String, track: Track): Unit = {
    data.update(id, track)
  }

  def get: Future[List[Track]] = {
    data.get
  }
}
