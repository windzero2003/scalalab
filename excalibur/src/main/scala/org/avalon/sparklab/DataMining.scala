package org.avalon.sparklab

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import org.apache.spark.sql.DataFrame


object DataMining {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Spark Example")
      .master("local")
      .getOrCreate()
    
    import spark.implicits._
    import spark.sql
    
    val sourceDF = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/ddm_stock")
      .option("dbtable", "ddm_stock.stock_benefit_report")
      .option("user", "dbuser")
      .option("password", "123456")
      .option("useSSL", "true")
      .load()
    
    
   val df = sourceDF.filter($"report_date" > "2015-01-01")
    
    val num =  df.count()
    
    println(num)
    
    df.show()
    
    
  }
}