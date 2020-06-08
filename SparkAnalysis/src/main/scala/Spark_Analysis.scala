package Spark_Analysis
import org.apache.spark
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.util.parsing.json.JSON
import org.apache.spark.sql.{DataFrame, SparkSession}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Spark_Analysis {


  def loadData() = {
    // Create the spark configuration and spark context
  /*  val conf = new SparkConf()
      .setAppName("ticket-analysis")
      .setMaster("local[*]")

    val sc = SparkContext.getOrCreate(conf)
    val rddFromFile = sc.textFile("hdfs://localhost:9000/prestacop-storage/")
    rddFromFile*/

    val spark = SparkSession.builder()
      .appName("ticket-analysis")
      .master("local[*]")
      .getOrCreate()
    spark.read.parquet("hdfs://localhost:9000/prestacop-storage/")
  }

  def getDayOfWeek(datetime:String)=
    {
      val date = datetime.split(" ")(0)
      val df = DateTimeFormatter.ofPattern("MM/dd/yyyy")
      val dayOfWeek = LocalDate.parse(date, df).getDayOfWeek
      dayOfWeek
    }
  def getMonth(datetime:String)=
  {
    val date = datetime.split(" ")(0)
    val df = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    val month = LocalDate.parse(date, df).getMonth
    month
  }

  def getViolationPerDay() : Unit=
    {
      val spark = SparkSession.builder()
        .appName("ticket-analysis")
        .master("local[*]")
        .getOrCreate()
      import spark.implicits._
      val df = spark.read.parquet("hdfs://localhost:9000/prestacop-storage/")
      val days = df.collect().map{x => getDayOfWeek(x(3).toString).toString}
      val monday = days.count(x => x == "MONDAY")
      val tuesday = days.count(x => x == "TUESDAY")
      val wednesday = days.count(x => x == "WEDNESDAY")
      val thursday = days.count(x => x == "THURSDAY")
      val friday = days.count(x => x == "FRIDAY")
      val saturday = days.count(x => x == "SATURDAY")
      val sunday = days.count(x => x == "SUNDAY")
      val weekend = saturday + sunday
      val week = monday + tuesday + wednesday + thursday + friday
      //TODO: Nb violations per days
      println("Monday: " + monday)
      println("Tuesday: " + tuesday)
      println("Wednesday: " + wednesday)
      println("Thursday: " + thursday)
      println("Friday: " + friday)
      println("Saturday: " + saturday)
      println("Sunday: " + sunday)
      //TODO: Nb violations during the week-end
      println("Week-end: " + weekend)
      //TODO: Nb violations during the weeb
      println("Week: " + week)

    }

  def getViolationCode() : Unit=
  {
    val df = loadData()
    val alerts = df.collect().map(x => x(4)).count(x => x != null)
    val noalerts = df.collect().map(x => x(4)).count(x => x == null)
    //TODO: Nb violations with an alert
    println("alerts: " + alerts)
    //TODO: Nb Violation without alert
    println("no alerts: " + noalerts)

  }

  def getViolationPerMonth()={
    val spark = SparkSession.builder()
      .appName("ticket-analysis")
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._
    val df = spark.read.parquet("hdfs://localhost:9000/prestacop-storage/")
    val months = df.collect().map{x => getMonth(x(3).toString).toString}
    val jan = months.count(x => x == "JANUARY")
    val feb = months.count(x => x == "FEBRUARY")
    val mar = months.count(x => x == "MARCH")
    val apr = months.count(x => x == "APRIL")
    val may = months.count(x => x == "MAY")
    val jun = months.count(x => x == "JUNE")
    val jul = months.count(x => x == "JULY")
    val aug = months.count(x => x == "AUGUST")
    val sep = months.count(x => x == "SEPTEMBER")
    val oct = months.count(x => x == "OCTOBER")
    val nov = months.count(x => x == "NOVEMBER")
    val dec = months.count(x => x == "DECEMBER")
    val summer_break = aug + jul

    //TODO: phrase : Nb violations per months
    println("January: " + jan)
    println("February: " + feb)
    println("March: " + mar)
    println("April: " + apr)
    println("May: " + may)
    println("June: " + jun)
    println("July: " + jul)
    println("August: " + aug)
    println("September: " + sep)
    println("October: " + oct)
    println("November: " + nov)
    println("December: " + dec)

    //TODO: Nb violations during summer break
    println("Summer Break : " + summer_break )
  }
  def main(args: Array[String]): Unit = {

    getViolationCode()
    getViolationPerDay()
    getViolationPerMonth()

  }
}
