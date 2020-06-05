name := "SparkStreaming"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.4.2"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.4.2"
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "2.4.0"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.4.2"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.4.2"
libraryDependencies += "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.4.2"

