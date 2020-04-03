package tracks.datasource

import tracks.models.Track

import scala.concurrent.Future

trait DataStore {

  def length(): Int

  def add(track: Track)

  def remove(id: String)

  def update(id: String, track: Track)

  def get: Future[List[Track]]

  def getById(id: String): Future[Option[Track]]

}
