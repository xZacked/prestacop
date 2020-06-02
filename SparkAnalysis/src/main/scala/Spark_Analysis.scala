package Spark_Analysis
import org.apache.spark
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import scala.util.parsing.json.JSON
import org.apache.spark.sql.SparkSession
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Spark_Analysis {


  def loadData() = {
    // Create the spark configuration and spark context
   /* val conf = new SparkConf()
      .setAppName("ticket-analysis")
      .setMaster("local[*]")

    val sc = SparkContext.getOrCreate(conf)
    val rddFromFile = sc.textFile("hdfs://localhost:9000/prestacop/")
    rddFromFile
*/
    val spark = SparkSession.builder()
      .appName("ticket-analysis")
      .master("local[*]")
      .getOrCreate()
    spark.read.json("hdfs://localhost:9000/prestacop/")
  }

  def getDayOfWeek(date:String)=
    {
      val df = DateTimeFormatter.ofPattern("MM/dd/yyyy")
      val dayOfWeek = LocalDate.parse(date, df).getDayOfWeek
      dayOfWeek
    }

  def main(args: Array[String]): Unit = {
    val a = loadData()
    //val b  = a.collect().flatMap(x => JSON.parseFull(x))

    //a.collect().map(x => println((x)))
    a.collect().map(x => println(x(3)))
    //println(getDayOfWeek("06/02/2020"))
  }
}
