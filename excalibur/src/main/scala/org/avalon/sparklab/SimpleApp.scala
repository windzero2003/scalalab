package org.avalon.sparklab

/* SimpleApp.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.SparkConf

import scala.collection.mutable.ListBuffer

object SimpleApp {
  def main(args: Array[String])/*: Unit = */{
    val logFile = "file:/usr/local/spark/README.md"
    //spark://master:7077 local[4]
    //local[4]
    
    val jars = ListBuffer[String]()
    
    /*args.foreach(f=>{println(f)})
    
    args(0).split(',').map(jars += _)*/
    val pathtoJars = System.getProperty("user.dir")+"/target/scalalab-0.0.1-SNAPSHOT.jar"
    println(pathtoJars)
    
    //${build_project}
    println(System.getenv("SPARK_HOME"))
    
    val conf = new SparkConf().setMaster("spark://master:7077").setAppName("Simple Application")  
      .setJars(List(pathtoJars))
      //.setSparkHome(System.getenv("SPARK_HOME")) 
      //.set("spark.executor.memory", "480m")
      //.set("spark.cores.max", "1")
      //.set("spark.driver.host", "192.168.137.3")
    
   //println(conf.get("spark.driver.host"))
      
      
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile,2).cache()
    val numAs = logData.filter(line=>line.contains("a")).count()
    val numBs = logData.filter(line=>line.contains("b")).count()
    
    
    
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    sc.stop()
    
    
    
    
  }
}