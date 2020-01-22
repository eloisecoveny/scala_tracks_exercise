package tracks.client

import java.sql.{Connection, PreparedStatement}

abstract class DBClient {

  val statement: PreparedStatement

  def createTable {

      statement.executeQuery(
        "CREATE TABLE music.tracks " +
          "type text NOT NULL, " +
          "id text NOT NULL, " +
          "urn text NOT NULL, " +
          "titles json NULL, " +
          "availability json NULL "
        );
  }

  def insertOrUpdateTrack(json: String) {

    statement.executeQuery(
      "INSERT INTO music.tracks " +
      "(type, id, urn, titles, availability) " +
      "VALUES (?, ?, ?, ?::JSON, ?::JSON) " +
      "type = excluded.type, " +
      "id = excluded.id, " +
      "urn = excluded.urn, " +
      "titles = excluded.titles, " +
      "availability = excluded.availability "
    );

  }
}
