package org.avalon.sparklab

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import org.apache.spark.sql.DataFrame

import java.util.Date

object SelectTime {

  
  case class Person(name: String, age: Long)
  
  //Date
  case class IFMinData(time: String, openPrice: Double, highestPrice: Double, lowestPrice: Double, closePrice: Double,
                       tradingAmount: Double, volume: Long)

  def main(args: Array[String]) /*: Unit = */ {

    // warehouseLocation points to the default location for managed databases and tables
    // spark-warehouse file:${system:user.dir}
    val warehouseLocation = "hdfs://master:9000/user/hive/warehouse"

    val spark = SparkSession
      .builder()
      .appName("Spark Example")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    import spark.sql
    
    /*val path = System.getProperty("user.dir")+"/src/main/resources/people.json"
    val peopleDS = spark.read.json(path).as[Person]
    peopleDS.show()*/

    
    //读取csv转换为json
    val df = spark.read.format("csv")
     
     .option("quote", "\"")
     .option("escape", "")
     .option("timestampFormat", "yyyy-MM-dd HH:mm:ss")
     .option("header", true)
     .load(System.getProperty("user.dir") + "/data/1.csv")
    //.select("openPrice", "volume")
    val it = df.toLocalIterator()
		//println(it.next())
		
		df.write.format("json").save(System.getProperty("user.dir") + "/data/2.json")
		
		//df.write.format("json").
	
		
    //执行json的解读
		/*val dfs = spark.read.json(System.getProperty("user.dir") + "/data/1.json")
		
		val ds = dfs.as[IFMinData]
    ds.show()*/
		//println(ds.toLocalIterator().next())
    
    
    //val ds = spark.read.option("header", true).csv(System.getProperty("user.dir") + "/data/47#IFL8.lc1.csv").as[IFMinData]
    //val ds = spark.read.format("csv").option("header", true).load(System.getProperty("user.dir") + "/data/47#IFL8.lc1.csv").as[IFMinData]

    
		//执行csv的解读
    /*val ds = spark.read
      .option("header", true)
      .option("quote", '\u0000')
      .option("timestampFormat", "yyyy-MM-dd HH:mm:ss")
      .csv(System.getProperty("user.dir") + "/data/1.csv")
      .as[IFMinData]
    
    ds.show()*/
    
    /*val it = ds.toLocalIterator()
    while (it.hasNext()) {
      println(it.next())
    }*/
    

  }

}