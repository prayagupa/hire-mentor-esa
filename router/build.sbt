name := """router"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.cloudphysics" % "jerkson_2.10" % "0.6.3",
  "net.liftweb" %% "lift-json" % "2.6.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.0.2", //just for fucking java
//  "com.fasterxml.jackson.module" % "jackson-module-scala" % "2.0.2",
  "org.mongodb" % "mongo-java-driver" % "3.1.0",
  "org.apache.kafka" % "kafka_2.11" % "0.8.2.2"
    exclude("javax.jms", "jms")
    exclude("com.sun.jdmk", "jmxtools")
    exclude("com.sun.jmx", "jmxri")
  //  "org.apache.camel" %% "camel-kafka" % "2.16.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
