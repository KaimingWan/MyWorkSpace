package streaming

import kafka.serializer.StringDecoder
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  *
  * @author Wan Kaiming on 2016/12/13
  * @version 1.0
  */
object SparkStreamingConsumer {

  def main(args: Array[String]): Unit = {

    //创建一些上下文信息
    val sparkConf = new SparkConf().setAppName("SparkStreamingConsumer")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(10)) //每10秒处理一次

    //kafka参数
    val Array(topics) = Array("my-topic-new")

    //创建direct kafka stream
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String](
      "bootstrap.servers" -> "10.8.12.139:9092,10.8.12.140:9092,10.8.12.144:9092"

    )
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

    //输出value值
    val lines = messages.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    wordCounts.print()


    ssc.start()
    ssc.awaitTermination()

  }

}
