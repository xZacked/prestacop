import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
import play.api.libs.json._


object Stream_Alert {
  val streamsConfiguration = new Properties()
  streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "myapp")
  streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass.getName)
  streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass.getName)

  streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  def main(args: Array[String]): Unit = {
    implicit val msg = Json.format[Message]
    val builder = new StreamsBuilder()
    val textLines = builder.stream[String, String]("drone_topic")
    val alert1 = textLines.filter((x,v) => Json.parse(v).as[Message].violation_code.isDefined)
    alert1.to("alert_topic")

    val streams = new KafkaStreams(builder.build(), streamsConfiguration)
    streams.start()

  }

}
