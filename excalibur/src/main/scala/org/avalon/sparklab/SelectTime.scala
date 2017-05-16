package org.avalon.sparklab

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import org.apache.spark.sql.DataFrame

object SelectTime {
  
  
  def main(args: Array[String]) /*: Unit = */ {

    // warehouseLocation points to the default location for managed databases and tables
    // spark-warehouse file:${system:user.dir}
    val warehouseLocation = "hdfs://master:9000/user/hive/warehouse"

    val spark = SparkSession
      .builder()
      .appName("Spark Example")
      .master("spark://local")
      .getOrCreate()

    import spark.implicits._
    import spark.sql

    
    


  }
  
  
}