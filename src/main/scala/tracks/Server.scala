package tracks

import java.sql.{Connection, DriverManager}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import tracks.api.Api
import tracks.config.Config
import tracks.datasource.DBSchema

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Server extends App
  with Api
  with Config {

  val host = "localhost"
  val port = 8080

  Class.forName(driverClassName)
  val dbConnection: Connection = DriverManager.getConnection(dbUrl, dbusername, dbpassword)

  try {
    DBSchema.createDbSchema(dbConnection)

    implicit val system: ActorSystem = ActorSystem("tracks")
    implicit val executor: ExecutionContext = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val bindingFuture = Http().bindAndHandle(routes, host, port)

    bindingFuture.onComplete {
      case Success(serverBinding) => println(s"listening to ${serverBinding.localAddress}")
      case Failure(error) => println(s"error: ${error.getMessage}")
    }

  } finally {
    dbConnection.close()
  }
}
