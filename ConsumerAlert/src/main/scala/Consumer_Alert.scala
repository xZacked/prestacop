import java.time.Duration
import java.util.Properties

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

object Consumer_Alert {
  def main(args: Array[String]): Unit = {
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
      records.asScala.foreach { record =>
        println(s"value = ${record.value()}")
      }
    }
    consumer.commitSync()
  }
}