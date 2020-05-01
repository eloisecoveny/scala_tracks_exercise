package tracks.config

import com.typesafe.config.{Config => AkkaConfig, ConfigFactory => AkkaConfigFactory}

trait Config {
  protected lazy val config: AkkaConfig = AkkaConfigFactory.load()

  lazy val serviceName: String = config.getString("service.name")

  lazy val httpInterface: String = config.getString("service.http.interface")
  lazy val httpPort: Int = config.getInt("service.http.port")
}
