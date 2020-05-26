import java.util.Properties
import scala.concurrent.duration._

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

object Drone_Simulator {
  val props: Properties = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])


  def drone2msg(iter: Int): Any = {
    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)
    val deadline = iter.seconds.fromNow
    while (deadline.hasTimeLeft) {
      val r = scala.util.Random
      val alert = r.nextInt(3)
      if (alert == 1)
        simulate_drone(producer, true)
      else
        simulate_drone(producer)
      Thread.sleep(1000)
    }
    producer.close()
  }


  def simulate_drone(producer: KafkaProducer[String, String], is_alert: Boolean = false ) {
    val r = scala.util.Random
    val drone_id = Some(r.nextInt(10))
    val time = r.nextInt(12).toString + '/' + r.nextInt(30).toString + "/2020 0" +
      r.nextInt(9).toString + r.nextInt(59).toString + 'A'
    val address =  r.alphanumeric.filter(_.isDigit).take(3).mkString + " W " +
      r.nextInt(99).toString + "th St"
    var plate_id : Option[String] = None
    var violation_code : Option[String] = None
    if (is_alert)
      {
        plate_id = Some(r.alphanumeric.take(7).mkString.toUpperCase)
        violation_code = Some((1 + r.nextInt((99 - 1) + 1)).toString)
      }

    val msg = Message(time = time, plate_id = plate_id, drone_id = drone_id,
      violation_code = violation_code, address = address)
    val record = new ProducerRecord[String, String]("drone_topic",
      play.api.libs.json.Json.obj(
        "drone_id" -> msg.drone_id,
        "plate_id" -> msg.plate_id,
        "address" -> msg.address,
        "time" -> msg.time,
        "violation_code" -> msg.violation_code
      ).toString)
    println("drone_id = " + msg.drone_id + ", plate_id = " + msg.plate_id + ", address = "
      + msg.address + ", time: " + msg.time + ", violation_code:" + msg.violation_code)
    producer.send(record)
  }

  def main(args: Array[String]): Unit = {
    drone2msg(10)
  }

}
