package com.hireartists.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static java.util.Arrays.asList;

/**
 * Created by prayagupd
 * on 11/23/15.
 */

public abstract class KafkaMessageListener  extends Thread {

    private Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    protected String ARTISTS_GROUP = "artist_group";
    public static final String DEFAULT_ZOOK_2181 = "127.0.0.1:2181";
    protected String TOPIC = "topic.artists";

    private KafkaConsumer<String, String> consumer;

    KafkaMessageListener(){
        Properties properties = new Properties(){{
            put("bootstrap.servers", "localhost:9092");
            put("group.id", ARTISTS_GROUP);
            put("enable.auto.commit", "true");
            put("auto.commit.interval.ms", "1000");
            put("session.timeout.ms", "30000");
            put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        }};
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(TOPIC));
    }

    @Override
    public void run() {
        System.out.println("Before KafkaMessageListener " + this.getThreadGroup().getName());

        while(true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);

            consumerRecords.forEach(consumerRecord -> {
                onMessage(consumerRecord);
            });
        }
    }

    public abstract void onMessage(ConsumerRecord<String, String> event);
}
