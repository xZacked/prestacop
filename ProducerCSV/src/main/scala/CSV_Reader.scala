import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import scala.io.Source

object CSV_Reader {
  val props: Properties = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])


  def csv2msg(path: String): Any = {
    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)
    val bufferedSource = Source.fromFile(path)

    val col_names = bufferedSource.getLines().next().split(',').toList

    bufferedSource.getLines()
      .drop(1)
      .foreach(line => line2msg(col_names, line.split(',').toList, producer))

    bufferedSource.close()
    producer.close()
  }

  def line2msg(col_names: List[String], line: List[String], producer: KafkaProducer[String, String]): Any = {
    line match {
      case x if line.size > col_names.indexOf("Violation Time") =>
        val plate_id = x(col_names.indexOf("Plate ID"))
        val address = Some(x(col_names.indexOf("House Number")) + " " + x(col_names.indexOf("Street Name")))
        val time = x(col_names.indexOf("Issue Date")) + " " + x(col_names.indexOf("Violation Time"))
        val violation_code = Some(x(col_names.indexOf("Violation Code")))

        val msg = Message(plate_id = plate_id, address = address, time = time, violation_code = violation_code)

        val record = new ProducerRecord[String, String]("csv_topic",
          play.api.libs.json.Json.obj(
            "plate_id" -> msg.plate_id,
            "address" -> msg.address,
            "time" -> msg.time,
            "violation_code" -> msg.violation_code
          ).toString)
        println("plate_id = " + msg.plate_id + ", address = " + msg.address + ", time = " + msg.time + "violation_code" -> msg.violation_code)
        producer.send(record) // ne pas utiliser get

      // case _ => Nil
      }
  }

  def main(args: Array[String]): Unit = {
    val path = "/home/xzacked/Documents/scala/prestacop/nyc-parking-tickets/Parking_Violations_Issued_-_Fiscal_Year_2017.csv"
    csv2msg(path)
  }

}