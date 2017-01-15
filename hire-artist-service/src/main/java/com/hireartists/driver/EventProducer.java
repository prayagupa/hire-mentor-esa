package com.hireartists.driver;

import com.hireartists.domains.Artist;
import com.hireartists.repository.ArtistRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class EventProducer {

    Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    public ArtistRepository artistRepository;
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
