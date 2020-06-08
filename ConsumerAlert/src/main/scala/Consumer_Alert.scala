import java.time.Duration
import java.util.Properties

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import play.api.libs.json.Json

object Consumer_Alert {
  def main(args: Array[String]): Unit = {
    def alert(message: Message): Unit = {
      println("Warning ! vehicle " + message.plate_id.get
        + " was caught committing offense number " + message.violation_code.get +
        " on " + message.time + " at the following address " + message.address)
    }
    implicit val msg = Json.format[Message]
    // Instantiate a producer
    val props: Properties = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "myconsumergroup") // ne pas set auto commit à true car on perd des msg

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(List("alert_topic").asJava)
    while (true){
      val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(100))
      records.asScala.foreach { record => alert(Json.parse(record.value).as[Message])
      }
    }
    consumer.commitSync()
  }
}