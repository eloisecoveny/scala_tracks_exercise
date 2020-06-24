package tracks.datasource

import scredis.Redis
import tracks.models.Track
import org.json4s._
import org.json4s.jackson.Serialization.write
import org.json4s.jackson.JsonMethods.parse

import scala.concurrent.Future

/*
Available Redis Queries

1. HDEL key [field...] -> Delete one or more hash fields
2. HEXISTS key field -> Determine is a hash field exists
3. HGET key field -> Get the value of a hash field
4. HGETALL key -> Get all the fields and values in a hash
5. HINCRBY key field increment -> Increment the integer value of a hash field by the given number
6. HINCRBYFLOAT key field increment -> Increment the float value of a hash field by the given amount
7. HKEYS key -> Get all the fields in a hash
8. HLEN key -> Get the number of fields in a hash
9. HMGET key field [field...] -> Get the values of all the given hash fields
10. HMSET key value field [field value...] -> Set multiple hash fields to multiple values
11. HSET key field value [field value...] -> Set the string value of a hash field
12. HSETNX key field value -> Set the value of a hash field, only if the field does not exist
13. HSTRLEN key field -> Get the length of the value of a hash field
14. HVALS key -> Get all the values in a hash
15. HSCAN key cursor [MATCH pattern...] -> Incrementally iterate hash fields and associated values
 */

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
