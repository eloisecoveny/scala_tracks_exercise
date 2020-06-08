import org.scalatest.{BeforeAndAfterEach, FlatSpec}
import org.scalatest.matchers.should.Matchers
import scredis.Redis
import tracks.datasource.RedisClient
import tracks.models.{Track, TrackAvailability, TrackTitles}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class RedisClientTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  val testRedisDataStore = new TestRedisClient

  override def afterEach() = {
    val beforeResult = Await.result(testRedisDataStore.length, Duration.Inf)
    println(s"Items in DB before deletion: $beforeResult")
    Await.result(testRedisDataStore.deleteAll(), Duration.Inf)
    val afterResult = Await.result(testRedisDataStore.length, Duration.Inf)
    println(s"Items in DB after deletion: $afterResult")
  }

  val track1 = Track(
    "track",
    "nznx3r",
    "urn:bbc:sounds:track:nznx3r",
    Some(TrackTitles(
      Some("AC/DC"),
      Some("Highway to Hell"),
      None)),
    Some(TrackAvailability(
      Some("2019-02-13T11:03:05Z"),
      Some("2019-03-15T11:00:00Z"),
      Some("Available for 29 days")
    )))

  "Tracks" should "Successfully write a new track to the Redis data store" in {
    testRedisDataStore.add(track1)
    val result = Await.result(testRedisDataStore.get, Duration.Inf)
    result.length should be (1)
  }

  it should "Successfully delete a track from the Redis data store" in {
    val initialResult = Await.result(testRedisDataStore.get, Duration.Inf)
    initialResult.length should be (0)
    testRedisDataStore.add(track1)
    testRedisDataStore.remove(track1.id)
//    val result = Await.result(testDataStore.get, Duration.Inf)
//    result.length should be (0)
  }
}

class TestRedisClient extends RedisClient {

  val testRedis = Redis()

  def deleteAll() = testRedis.del("*")

}
