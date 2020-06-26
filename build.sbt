name := "scala_tracks_exercise"

version := "1.3.4"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka"     %% "akka-actor"       % "2.5.26",
  "com.typesafe.akka"     %% "akka-stream"      % "2.5.26",
  "com.typesafe.akka"     %% "akka-http"        % "10.1.11",
  "org.json4s"            %% "json4s-core"      % "3.6.7",
  "org.json4s"            %% "json4s-jackson"   % "3.6.7",
  "org.json4s"            %% "json4s-ext"       % "3.6.7",
  "de.heikoseeberger"     %% "akka-http-json4s" % "1.29.1",
  "com.github.scredis"    %% "scredis"          % "2.3.3",
  "ch.qos.logback"         % "logback-classic"  % "1.1.3"   % Runtime,
  "org.scalatest"         %% "scalatest"        % "3.1.1"   % Test,
  "io.cucumber"           %% "cucumber-scala"   % "6.1.1"   % Test
)