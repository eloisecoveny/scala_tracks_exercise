import org.scalatest.{BeforeAndAfterEach, FlatSpec}
import org.scalatest.matchers.should.Matchers
import scredis.Redis
import tracks.datasource.RedisClient
import tracks.models.{Track, TrackAvailability, TrackTitles}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global

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
    testRedisDataStore
      .add(track1)
      .flatMap(_ => {
        testRedisDataStore.get
      })
      .map((tracks: Seq[Track]) => tracks.length should be (0))
  }

  it should "Successfully delete a track from the Redis data store" in {
    val initialResult = Await.result(testRedisDataStore.get, Duration.Inf)
    initialResult.length should be (0)
    Await.result(testRedisDataStore.add(track1), Duration.Inf)
    Await.result(testRedisDataStore.remove(track1.id), Duration.Inf)
    val result = Await.result(testRedisDataStore.get, Duration.Inf)
    result.length should be (0)
  }
}

class TestRedisClient extends RedisClient {
  import scala.concurrent.ExecutionContext.Implicits.global

  val testRedis = Redis()

  def deleteAll() = {
    testRedis.keys("*").flatMap(fKeys => {
      val keys = fKeys.mkString(" ")
      testRedis.del(keys)
    })
  }

}
