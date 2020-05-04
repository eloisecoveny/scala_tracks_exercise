import org.scalatest.{FlatSpec, Matchers}
import tracks.datasource.{DataStore, InMemoryDataStore}
import tracks.domain.Tracks
import tracks.models.{Track, TrackAvailability, TrackTitles}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class InMemoryDataStoreTest extends FlatSpec with Matchers {

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

  val track3 = Track(
    "track",
    "000001",
    "urn:bbc:sounds:track:000001",
    Some(TrackTitles(
      Some("Daniel Brown"),
      Some("Engineer"),
      None)),
    Some(TrackAvailability(
      Some("2019-02-13T11:03:05Z"),
      Some("2019-03-15T11:00:00Z"),
      Some("Available for 29 days")
    )))

  val track4 = Track(
    "track",
    "000002",
    "urn:bbc:sounds:track:000002",
    Some(TrackTitles(
      Some("Eloise Coveny"),
      Some("Cello"),
      None)),
    Some(TrackAvailability(
      Some("2019-02-13T11:03:05Z"),
      Some("2019-03-15T11:00:00Z"),
      Some("Available for 29 days")
    )))

  val track5 = Track(
    "track",
    "000003",
    "urn:bbc:sounds:track:000003",
    Some(TrackTitles(
      Some("Covid-19"),
      Some("Quarantine"),
      None)),
    Some(TrackAvailability(
      Some("2019-02-13T11:03:05Z"),
      Some("2019-03-15T11:00:00Z"),
      Some("Available for 29 days")
    )))

  val track6 = Track(
    "track",
    "000004",
    "urn:bbc:sounds:track:000004",
    Some(TrackTitles(
      Some("Plant Life"),
      Some("Green"),
      None)),
    Some(TrackAvailability(
      Some("2019-02-13T11:03:05Z"),
      Some("2019-03-15T11:00:00Z"),
      Some("Available for 29 days")
    )))


  "Tracks" should "Successfully write a new track to the data store" in {
    val testDataStore = new InMemoryDataStore

      testDataStore.add(track1)
      val result = Await.result(testDataStore.get, Duration.Inf)
      result.length should be (1)
    }

  it should "Successfully delete a track from the data store" in {
    val testDataStore = new InMemoryDataStore

    testDataStore.add(track1)
    testDataStore.remove(track1.id)
    val result = Await.result(testDataStore.get, Duration.Inf)
    result.length should be (0)
  }

  it should "Successfully update a track in the data store" in {
    val testDataStore = new InMemoryDataStore

    testDataStore.add(track1)
    testDataStore.update(track1.id, track2)
    val results = Await.result(testDataStore.get, Duration.Inf)
    results should contain (track2)
    results should not contain track1
  }

  it should "Successfully return a future list of tracks" in {
    val testDataStore = new InMemoryDataStore

    testDataStore.add(track1)
    val results = Await.result(testDataStore.get, Duration.Inf)
    results should have length 1
    results should contain (track1)
  }

  it should "Successfully return a track given a track id" in {
    val testDataStore = new InMemoryDataStore

    testDataStore.add(track1)
    val results = Await.result(testDataStore.getById(track1.id), Duration.Inf)
    results should be (Some(track1))
  }

  it should "Not remove anything when trying to remove a track that doesn't exist" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    val result1 = Await.result(testDataStore.get, Duration.Inf)
    result1.length should be (1)
    testDataStore.remove("000000")
    val result2 = Await.result(testDataStore.get, Duration.Inf)
    result2.length should be (1)
  }

  it should "Not get anything when trying to get by an id that doesn't exist" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    val result = Await.result(testDataStore.get, Duration.Inf)
    result.length should be (1)
    Await.result(testDataStore.getById("000000"), Duration.Inf) should be (None)
  }

  it should "Be able to get all tracks when were are 5 in the data store" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    testDataStore.add(track3)
    testDataStore.add(track4)
    testDataStore.add(track5)
    testDataStore.add(track6)
    val results1 = Await.result(testDataStore.get, Duration.Inf)
    results1.length should be (5)
    val results2 = Await.result(testDataStore.get, Duration.Inf)
    results2 should have length (5)
  }

  it should "Be able to get a track by id when were are 5 tracks in the data store" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    testDataStore.add(track3)
    testDataStore.add(track4)
    testDataStore.add(track5)
    testDataStore.add(track6)
    val result = Await.result(testDataStore.get, Duration.Inf)
    result.length should be (5)
    Await.result(testDataStore.getById("000004"), Duration.Inf) should be (Some(track6))
  }

  it should "Not get anything when there is nothing in the data store" in {
    val testDataStore = new InMemoryDataStore
    Await.result(testDataStore.get, Duration.Inf) should be (List.empty)
  }

  it should "Not get a track by id if that track id does not exist in the data store" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    Await.result(testDataStore.getById(track3.id), Duration.Inf) should be (None)
  }

  it should "Not add anything when trying add something with the same id that already exists" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    testDataStore.add(track2)
    Await.result(testDataStore.get, Duration.Inf) should have length 1
  }

  it should "Not remove a track that does not exist in the data store" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    testDataStore.remove(track3.id)
    Await.result(testDataStore.get, Duration.Inf) should have length 1
  }

  it should "Not update a track that does not exist in the data store" in {
    val testDataStore = new InMemoryDataStore
    testDataStore.add(track1)
    testDataStore.update(track3.id, track3)
    val results = Await.result(testDataStore.get, Duration.Inf)
    results should have length 1
    results should be (List(track1))
  }

}
