name := "scala_tracks_exercise"

version := "1.3.4"

scalaVersion := "2.13.3"

val akkaVersion = "2.5.26"
val akkaHttpVersion = "10.1.11"
val json4sVersion = "3.6.7"
val akkaHttpJson4sVersion = "1.29.1"
val scredisVersion = "2.3.3"
val logbackVersion = "1.1.3"
val scalatestVersion = "3.1.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka"     %% "akka-actor"       % akkaVersion,
  "com.typesafe.akka"     %% "akka-stream"      % akkaVersion,
  "com.typesafe.akka"     %% "akka-http"        % akkaHttpVersion,
  "org.json4s"            %% "json4s-core"      % json4sVersion,
  "org.json4s"            %% "json4s-jackson"   % json4sVersion,
  "org.json4s"            %% "json4s-ext"       % json4sVersion,
  "de.heikoseeberger"     %% "akka-http-json4s" % akkaHttpJson4sVersion,
  "com.github.scredis"    %% "scredis"          % scredisVersion,
  "ch.qos.logback"         % "logback-classic"  % logbackVersion    % Runtime,
  "org.scalatest"         %% "scalatest"        % scalatestVersion  % Test
)