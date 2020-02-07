package tracks.client

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, Statement}

class DBClient extends App {

  // Move to config
  val JDBC_DRIVER = "org.h2.Driver"
  val DB_URL = "jdbc:h2:./test"

  val USER = "sa"
  val PASS = "sa"

  // Place in method
  val conn: Connection = DriverManager.getConnection(DB_URL, USER, PASS)
//  val statement: PreparedStatement =
//
//  try {
//    Class.forName(JDBC_DRIVER)
//    val createTable: ResultSet = statement.executeQuery(createTableSql)
//  }

  val createTableSql = """
      |CREATE TABLE music.tracks IF NOT EXISTS
      |type text NOT NULL,
      |id text NOT NULL,
      |urn text NOT NULL,
      |titles json NULL,
      |availability json NULL
      |""".stripMargin

  def insertTrack(json: String): Unit = {
    val insertTrackSql = """
      |INSERT INTO music.tracks (type, id, urn, titles, availability)
      |VALUES (?, ?, ?, ?::JSON, ?::JSON)
      |type = excluded.type
      |id = excluded.id
      |urn = excluded.urn
      |titles = excluded.titles
      |availability = excluded.availability
      |""".stripMargin
//    statement.executeQuery(insertTrackSql)
  }
}
