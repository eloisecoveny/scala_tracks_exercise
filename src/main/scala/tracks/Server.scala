package tracks

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scredis.Redis
import tracks.api.Api

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Server extends App
  with Api {

  val host = "localhost"
  val port = 8080

  implicit val system: ActorSystem = ActorSystem("tracks")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val bindingFuture = Http().bindAndHandle(routes, host, port)
  bindingFuture.onComplete {
    case Success(serverBinding) => println(s"listening to ${serverBinding.localAddress}")
    case Failure(error) => println(s"error: ${error.getMessage}")
  }

  val redis = Redis()
  import redis.dispatcher
  redis.hGetAll("foo") onComplete {
    case Success(content) => println(content)
    case Failure(e) => e.printStackTrace()
  }
}
