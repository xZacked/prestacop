import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{DataTypes, StructType}

object Spark_Streaming {
  
  def stream_to_storage()= {

    val schema =  new StructType()
      .add("address", DataTypes.StringType)
      .add("drone_id",DataTypes.IntegerType)
      .add("plate_id", DataTypes.StringType)
      .add("time", DataTypes.StringType)
      .add("violation_code", DataTypes.StringType)

    val spark = SparkSession.builder()
      .appName("stream-to-storage")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "drone_topic")
      .load()

    val d = df.selectExpr("CAST(value AS STRING)")
        .as[(String)]

  val nestedDF = d.select(from_json($"value", schema).as("drone_info"))
  val flattenNestedDF = nestedDF.selectExpr("drone_info.address",
    "drone_info.drone_id", "drone_info.plate_id", "drone_info.time",
    "drone_info.violation_code")

    flattenNestedDF.writeStream
      .format("parquet")
      .option("path", "hdfs://localhost:9000/prestacop-storage/")
      .option("checkpointLocation","checkpoints")
      .trigger(Trigger.ProcessingTime("60 seconds"))
      .start("hdfs://localhost:9000/prestacop-storage/")
      .awaitTermination()
  }
  def main(args: Array[String]): Unit = {
    stream_to_storage()
  }
}


