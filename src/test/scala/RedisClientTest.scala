import org.scalatest.{BeforeAndAfterEach, FlatSpec}
import org.scalatest.matchers.should.Matchers
import scredis.Redis
import tracks.datasource.RedisClient
import tracks.models.{Track, TrackAvailability, TrackTitles}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class RedisClientTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  val testRedisDataStore = new TestRedisClient

  override def afterEach() = {
    val result = for {
      beforeResult <- testRedisDataStore.length
      _ <- testRedisDataStore.deleteAll()
      afterResult <- testRedisDataStore.length
    } yield {
      println(s"Items in DB before deletion: $beforeResult")
      println(s"Items in DB after deletion: $afterResult")
    }
    Await.result(result, 10.seconds)
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

  val track2 = Track(
    "track",
    "nznx3r",
    "urn:bbc:sounds:track:nznx3r",
    Some(TrackTitles(
      Some("Aldous Harding"),
      Some("Designer"),
      None)),
    Some(TrackAvailability(
      Some("2019-02-13T11:03:05Z"),
      Some("2019-03-15T11:00:00Z"),
      Some("Available for 29 days")
    )))

  "Tracks" should "Successfully write a new track to the Redis data store" in {
    val result = for {
      _ <- testRedisDataStore.add(track1)
      tracks <- testRedisDataStore.get
    } yield {
      tracks.length shouldBe (1)
    }
    Await.result(result, 10.seconds)
  }

  it should "Successfully delete a track from the Redis data store" in {
    val result = for {
      _ <- testRedisDataStore.add(track1)
      _ <- testRedisDataStore.remove(track1.id)
      tracks <- testRedisDataStore.get
    } yield {
      tracks.length shouldBe 0
    }
    Await.result(result, 10.seconds)
  }

  it should "Successfully update a track in the Redis data store" in {
    val result = for {
      _ <- testRedisDataStore.add(track1)
      _ <- testRedisDataStore.update(track2.id, track2)
      tracks <- testRedisDataStore.get
    } yield {
      tracks.map(track => track shouldBe Some(track2))
    }
    Await.result(result, 10.seconds)
  }

  it should "Successfully get a track by ID from the Redis data store" in {
    val result = for {
      _ <- testRedisDataStore.add(track1)
      track <- testRedisDataStore.getById(track1.id)
    } yield {
      track should be(Some(track1))
    }
    Await.result(result, 10.seconds)
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
