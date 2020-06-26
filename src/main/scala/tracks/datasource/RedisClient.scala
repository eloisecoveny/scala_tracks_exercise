package tracks.datasource

import scredis.Redis
import tracks.models.Track
import org.json4s._
import org.json4s.jackson.Serialization.write
import org.json4s.jackson.JsonMethods.parse

import scala.concurrent.Future

class RedisClient {

  implicit val formats = DefaultFormats

  val redis = Redis()

  import redis.dispatcher

  def length = redis.dbSize

  def add(track: Track) = {
    redis.exists(track.id).map(result => {
      if (!result) {
        val trackJson = write(track)
        redis.set(track.id, trackJson)
      }
    })
  }

  def remove(id: String) = {
    redis.exists(id).map(result => {
      if (result) redis.del(id)
      else println(s"There is no track with id: $id")
    })
  }

  def update(id: String, track: Track) = {
    redis.exists(id).map(result => {
      if (result) {
        redis.del(id)
        add(track)
      } else println(s"There is no track with id: $id")
    })
  }

  def get: Future[List[Track]] = {
    val fKeys: Future[Set[String]] = redis.keys("*")
    for {
      keys <- fKeys
      tracks <- redis.mGet(keys.mkString(" "))
    } yield {
      tracks.flatten.map(parse(_).extract[Track])
    }
  }

  def getById(id: String): Future[Option[Track]] = {
    val fTrack = redis.get(id)
      for {
        track <- fTrack
      } yield {
      track.map(parse(_).extract[Track])
    }
  }

}
