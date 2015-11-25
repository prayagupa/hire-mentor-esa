package service

import java.util.Properties

import kafka.javaapi.producer._
import kafka.producer.{KeyedMessage, ProducerConfig}

import scala.collection.mutable

/**
 * Created by prayagupd
 * on 11/21/15.
 */

class TopicService {
  var producer : Producer[Int, String] = null

  {
    val props = new Properties() {
      put("serializer.class", "kafka.serializer.StringEncoder")
      put("zk.connect", "localhost:2181")
      put("metadata.broker.list", "localhost:9092")
    }
    // Use random partitioner.
    // Don't need the key type
    // Just set it to Int
    producer = new Producer[Int, String](new ProducerConfig(props))
  }

  def sendMessageToTopic(message : String, topic: String): Unit = {
    println(s"sending ${message} to topic ${topic}")
    producer.send(new KeyedMessage[Int, String](topic, message))
  }
}
