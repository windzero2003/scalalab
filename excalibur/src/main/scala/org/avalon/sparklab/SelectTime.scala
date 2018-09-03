package org.avalon.sparklab

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import org.apache.spark.sql.DataFrame

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

object SelectTime {

  case class Person(name: String, age: Long)

  //Date
  case class IFMinData(time: Date, openPrice: Double, highestPrice: Double, lowestPrice: Double, closePrice: Double,
                       tradingAmount: Double, volume: Long)

  def main(args: Array[String]) /*: Unit = */ {

    // warehouseLocation points to the default location for managed databases and tables
    // spark-warehouse file:${system:user.dir}
    // val warehouseLocation = "hdfs://master:9000/user/hive/warehouse"

    val spark = SparkSession
      .builder()
      .appName("Spark Example")
      .master("local")
      .getOrCreate()

    import spark.implicits._
    import spark.sql

    /*val jdbcDF = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/ddm_stock")
      .option("dbtable", "ddm_stock.stock_day_bar_data")
      .option("user", "dbuser")
      .option("password", "123456")
      .load()*/
      

    /*val path = System.getProperty("user.dir")+"/src/main/resources/people.json"
    val peopleDS = spark.read.json(path).as[Person]
    peopleDS.show()*/

   

    //执行json的解读  
    val df = spark.read.json(System.getProperty("user.dir") + "/data/47#IFL8.lc1.json")
    //df.printSchema()

    
    df.show()
    
    val headTimeStr = df.select($"time").as("Date").orderBy($"time" desc).collect().last.getString(0);
    println("...")
    println(headTimeStr)
    
    
    val fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val fm2 = new SimpleDateFormat("yyyy-MM-dd")
    //val headTimeDate = fm.parse(headTimeStr)
    
    val headTimeDate = Calendar.getInstance
    
    headTimeDate.setTime(fm1.parse(headTimeStr))
    
    var oneMinuteBars = df
    
    if (headTimeDate.get(Calendar.HOUR) == 9 && headTimeDate.get(Calendar.MINUTE) == 30 && headTimeDate.get(Calendar.SECOND) == 0){
      println("标准的开盘时间，直接执行")

    }else{
      
      println("非标准的开盘时间，跳过一天"+headTimeStr)
      headTimeDate.add(Calendar.DATE, 1)
      
      headTimeDate.set(Calendar.HOUR, 9)
      headTimeDate.set(Calendar.MINUTE, 30)
      headTimeDate.set(Calendar.SECOND, 0)
      
      
      val afterDay = fm1.format(headTimeDate.getTime)
      println(afterDay)
      
      //df.filter($"time" >= afterDay).show()
      
      oneMinuteBars = df.filter($"time" >= afterDay)
      
      
      
      //oneMinuteBars.foreach(f)
      
    }
    
    oneMinuteBars.show()
    
    oneMinuteBars.foreach(x=> {
      
      
      }
    )
    
    
    
    
    //println(df.collect().max)
    
   /* df.filter($"time" like "2012-09-13%").show()
    //df.show()
    df.createOrReplaceTempView("iflc1")
    
    val results = spark.sql("SELECT time FROM iflc1").as("String")
    
    println(results)
    */
    //val ds = df.as[IFMinData]    
    //ds.show()
    
    
    
    
    //println(ds.toLocalIterator().next())

    //ds.m
    
    
    
    

    /*val it = ds.toLocalIterator()
    while (it.hasNext()) {
      println(it.next())
    }*/

  }

}