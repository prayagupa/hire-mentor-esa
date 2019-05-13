package hireartists.driver;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by prayagupd
 * on 11/23/15.
 */

public abstract class EventConsumer extends Thread {

    private Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    protected String ARTISTS_GROUP = "artist_group";
    protected String TOPIC = "topic.artists";

    private KafkaConsumer<String, String> consumer;

    public EventConsumer(){
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

        System.out.println("=============================================================");
        System.out.println("Before EventConsumer " + this.getThreadGroup().getName());
        System.out.println("=============================================================");

        while(true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);

            consumerRecords.forEach(this::onMessage);
        }
    }

    public abstract void onMessage(ConsumerRecord<String, String> event);
}
