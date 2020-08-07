name := "scala_tracks_exercise"

version := "1.3.4"

scalaVersion := "2.12.8"

val akkaVersion = "2.5.26"
val akkaHttpVersion = "10.1.11"
val json4sVersion = "3.6.7"
val akkaHttpJson4sVersion = "1.29.1"
val scredisVersion = "2.3.3"
val logbackVersion = "1.1.3"
val scalatestVersion = "3.1.1"
val cucumberVersion = "4.3.0"
val cucumberRunnerVersion = "0.2.0"

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
  "com.waioeka.sbt"       %% "cucumber-runner"  % cucumberRunnerVersion,
  "org.scalatest"         %% "scalatest"        % scalatestVersion  % Test,
  "io.cucumber"           %% "cucumber-scala"   % cucumberVersion   % Test,
  "io.cucumber"            % "cucumber-core"    % cucumberVersion   % Test,
  "io.cucumber"            % "cucumber-jvm"     % cucumberVersion   % Test,
  "io.cucumber"            % "cucumber-junit"   % cucumberVersion   % Test
)

val framework = new TestFramework("com.waioeka.sbt.runner.CucumberFramework")
testFrameworks += framework
testOptions in Test += Tests.Argument(framework,"--glue","src/test/scala/StepDefinitions")
testOptions in Test += Tests.Argument(framework,"--plugin","pretty")
parallelExecution in Test := false