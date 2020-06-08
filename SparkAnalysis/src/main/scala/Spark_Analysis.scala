package Spark_Analysis
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.SparkSession


object Spark_Analysis {

  def getDayOfWeek(datetime:String)=
    {
      try {
        val date = datetime.split(" ")(0)
        val df = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val dayOfWeek = LocalDate.parse(date, df).getDayOfWeek
        dayOfWeek
      } catch {
        case _ => "not parsed"
      }
    }
  def getMonth(datetime:String)=
  {
    try {
      val date = datetime.split(" ")(0)
      val df = DateTimeFormatter.ofPattern("MM/dd/yyyy")
      val month = LocalDate.parse(date, df).getMonth
      month
    } catch {
      case _ => "not parsed"
    }
  }

 def main(args: Array[String]): Unit = {
   val spark = SparkSession.builder()
     .appName("ticket-analysis")
     .master("local[*]")
     .getOrCreate()
   spark.sparkContext.setLogLevel("ERROR")
   val df = spark.read.parquet("hdfs://localhost:9000/prestacop-storage/")
   val tot = df.count().toDouble

   println("\nANALYSIS ON PRESTACOP DATA:\n")

   val alerts = df.collect().map(x => x(4)).count(x => x != null)
   val noalerts = df.collect().map(x => x(4)).count(x => x == null)
   println("NUMBER OF VIOLATION TRIGGERING AN ALERTS:")
   println(alerts + " | " + "%2.3f".format(alerts.toDouble/tot*100) + "%")
   println("NUMBER OF VIOLATIONS NOT TRIGERRING AN ALERT:")
   println(noalerts + " | " + "%2.3f".format(noalerts.toDouble/tot*100) + "%")

   println()

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
   println("NUMBER OF VIOLATIONS REGARDING THE DAY OF THE WEEK:")
   println("Monday: " + monday + " | " + "%2.2f".format(monday.toDouble/tot*100) + "%")
   println("Tuesday: " + tuesday + " | " + "%2.2f".format(tuesday.toDouble/tot*100) + "%")
   println("Wednesday: " + wednesday + " | " + "%2.2f".format(wednesday.toDouble/tot*100) + "%")
   println("Thursday: " + thursday + " | " + "%2.2f".format(thursday.toDouble/tot*100) + "%")
   println("Friday: " + friday + " | " + "%2.2f".format(friday.toDouble/tot*100) + "%")
   println("Saturday: " + saturday + " | " + "%2.2f".format(saturday.toDouble/tot*100) + "%")
   println("Sunday: " + sunday + " | " + "%2.2f".format(sunday.toDouble/tot*100) + "%")
   println()
   println("NUMBER OF VIOLATIONS DURING THE WEEKENDS:")
   println("Weekend: " + weekend + " | " + "%2.2f".format(weekend.toDouble/tot*100) + "%")
   println()
   println("NUMBER OF VIOLATIONS DURING THE WEEKS:")
   println("Week: " + week + " | " + "%2.2f".format(week.toDouble/tot*100) + "%")

   println()

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

   println("NUMBER OF VIOLATIONS FOR EACH MONTH:")
   println("January: " + jan + " | " + "%2.2f".format(jan.toDouble/tot*100) + "%")
   println("February: " + feb + " | " + "%2.2f".format(feb.toDouble/tot*100) + "%")
   println("March: " + mar + " | " + "%2.2f".format(mar.toDouble/tot*100) + "%")
   println("April: " + apr + " | " + "%2.2f".format(apr.toDouble/tot*100) + "%")
   println("May: " + may + " | " + "%2.2f".format(may.toDouble/tot*100) + "%")
   println("June: " + jun + " | " + "%2.2f".format(jun.toDouble/tot*100) + "%")
   println("July: " + jul + " | " + "%2.2f".format(jul.toDouble/tot*100) + "%")
   println("August: " + aug + " | " + "%2.2f".format(aug.toDouble/tot*100) + "%")
   println("September: " + sep + " | " + "%2.2f".format(sep.toDouble/tot*100) + "%")
   println("October: " + oct + " | " + "%2.2f".format(oct.toDouble/tot*100) + "%")
   println("November: " + nov + " | " + "%2.2f".format(nov.toDouble/tot*100) + "%")
   println("December: " + dec + " | " + "%2.2f".format(dec.toDouble/tot*100) + "%")
   println()

   println("NUMBER OF VIOLATIONS DURING SUMMER BREAK:")
   println(summer_break + " | " + "%2.2f".format(summer_break.toDouble/tot*100) + "%")
 }
}
