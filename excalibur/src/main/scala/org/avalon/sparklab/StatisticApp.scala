package org.avalon.sparklab

/* StatisticApp.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import org.apache.spark.sql.DataFrame

object StatisticApp {
  
  
  
  
  
  def func1(stockCode:String,dayBars: DataFrame):Unit={
    
    println("dealing with "+stockCode)
    
    dayBars.printSchema()
    
    

    
    //dayBars.filter($"stock_code" === "123").show(10)
    
  }
  
  
  def main(args: Array[String]) /*: Unit = */ {

    // warehouseLocation points to the default location for managed databases and tables
    // spark-warehouse file:${system:user.dir}
    val warehouseLocation = "hdfs://master:9000/user/hive/warehouse"

    val spark = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .master("spark://master:7077")
      .config("hive.metastore.uris", "thrift://localhost:9083")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql

    /*
    var dayBars = sql("SELECT * FROM ddm_stock.stock_day_bar_data").toDF("stock_code","open_price","highest_price","lowest_price","close_price","pre_close_price",
        "change", "change_percent", "turnover_rate", "volume", "trading_amount", "total_market_value", "tradable_market_value", "aa_factor")
    */

    //sql("SELECT * FROM ddm_stock.stock where stock_name like 'PT%' ").show()

    val stocks = sql("SELECT * FROM ddm_stock.stock where stock_name not like 'PT%' ").toDF()

    val exclude_stocks = sql("SELECT stock_code FROM ddm_stock.stock where stock_name like 'PT%' ").toDF()
    //stocks.cache()

    //println(stocks.count())
    val dayBars = sql("SELECT * FROM ddm_stock.stock_day_bar_data where stock_code not in (SELECT stock_code FROM ddm_stock.stock where stock_name like 'PT%') ").toDF()

    //dayBars.cache()
    
    
    
    
    val ds = stocks.select("stock_code").as("String")
    

    /*ds.collect().foreach{f=>

      dayBars.filter($"stock_code" === f.getAs[String]("stock_code")).show(10)

    }*/
    
    
    dayBars.filter($"stock_code" === "600755").show(10)
    

    
    
    
    //stocks.printSchema()
    
    //stocks.select($"stock_code", $"stock_name").show()
    
    
    
    
  
    
    
    
    
    //stocks.foreach(x=>println(x))
    
    /*for( stock_code<-stocks){
      //bug
      println(stock_code)
      
    }*/
    

    //stocks.select($"stock_code",$"stock_name",$"symbol").show()

    
    
    //("stock_code","open_price","highest_price","lowest_price","close_price","pre_close_price",
    //    "change", "change_percent", "turnover_rate", "volume", "trading_amount", "total_market_value", "tradable_market_value", "aa_factor")

    //dayBars.show(3);

    //println(dayBars.count())

    // Load training data
    //val training = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
    //spark.read.format("libsvm").parquet(path);

  }
}