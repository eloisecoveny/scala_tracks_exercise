package tracks.datasource

import java.sql.Connection

object DBSchema {

  def createDbSchema(conn: Connection) = {

    val sql =
      """
        |create schema if not exists tracks (
        |type String not null,
        |id String primary key,
        |urn String not null,
        |primaryTitle String,
        |secondaryTitle String,
        |tertiaryTitle String,
        |availabilityfrom String,
        |availabilityTo String,
        |availabilityLabel String);
        |""".stripMargin
    val statement = conn.createStatement()
    try {
      statement.execute(sql)
    } finally {
      statement.close()
    }
  }

}
