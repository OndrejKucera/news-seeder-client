import sbtassembly.MergeStrategy

name := "news-seeder-client"
organization := "org.news"

version := "0.1.0"
scalaVersion := "2.12.4"

scalacOptions ++= Seq(
  "-encoding",
  "utf-8",
  "-explaintypes",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-language:postfixOps",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Xcheckinit",
  "-Xlint",
  "-Xlint:inaccessible",
  "-Xlint:nullary-override",
  "-Xlint:nullary-unit",
  "-Xlint:option-implicit",
  "-Xlint:package-object-classes",
  "-Xlint:poly-implicit-overload",
  "-Xlint:private-shadow",
  "-Xlint:unsound-match",
  "-Xlint:missing-interpolator",
  "-Xfuture",
  "-Yrangepos",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-value-discard",
  "-Ywarn-unused-import",
  "-Ywarn-unused"
)

val akkaV = "2.5.9"
val akkaHttpV = "10.0.11"
val scalaTestV = "3.0.4"

resolvers ++= Seq(
  "Apache Repository" at "https://repository.apache.org/content/repositories/releases/",
)

libraryDependencies ++= Seq(
  "com.typesafe.akka"   %% "akka-http"     % akkaHttpV  % "compile",
  "org.scalatest"       %% "scalatest"     % scalaTestV % "test",

  "org.apache.hadoop"   % "hadoop-common"  % "2.7.5"
    exclude ("com.google.guava", "guava")
    exclude ("io.netty", "netty"),
  "org.apache.hbase"    % "hbase-common"   % "1.4.0",
  "org.apache.hbase"    % "hbase-client"   % "1.4.0"
)

// use /tmp for building assembly to avoid IO between docker vm and host
assemblyOption in assembly := (assemblyOption in assembly).value
  .copy(assemblyDirectory = new File("/tmp/sbt-assembly"))

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)

}