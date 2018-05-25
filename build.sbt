lazy val akkaHttpVersion = "10.1.1"
lazy val akkaVersion     = "2.5.12"
lazy val alpakkaVersion  = "0.19"

lazy val root = (project in file(".")).
  settings(
    name            := "news-seeder-client",
    version         := "0.2.0-SNAPSHOT",
    organization    := "org.news",
    scalaVersion    := "2.12.6",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

      "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % alpakkaVersion,

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test
    )
  )