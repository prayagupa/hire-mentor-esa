package com.es.driver;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Properties;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

public class EventProducer {

    Logger logger = LoggerFactory.getLogger(EventProducer.class);

    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private final String topic = "topic.artists";

    public EventProducer() {
        Properties config = new Properties() {{
            put("bootstrap.servers", "localhost:9092");
            put("enable.auto.commit", "true");
            put("auto.commit.interval.ms", "1000");
            put("session.timeout.ms", "30000");
            put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            put("partition.assignment.strategy", "range");
        }};
        producer = new KafkaProducer<>(config);
    }

    public void produce(String message) {
        System.out.println("producing");
        producer.send(new ProducerRecord<String, String>(topic, "", message.toString()));
    }

    @PostConstruct
    public void init() {
        logger.info("initializing EventProducer " + new Date());
    }
}
