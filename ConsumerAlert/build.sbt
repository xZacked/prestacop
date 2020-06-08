name := "ConsumerAlert"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.5.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "2.1.0"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.12"