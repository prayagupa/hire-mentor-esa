package com.hireartists.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by prayagupd
 * on 11/23/15.
 */

@Component
@Scope("prototype")
public class ArtistEventListener extends KafkaMessageListener {

    private Logger logger = LoggerFactory.getLogger(ArtistEventListener.class);

    public ArtistEventListener() {
        TOPIC = "topic.artists";
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> event) {
        System.out.println("Start " + new Date() + "========== consuming ============= {} " + this.getThreadGroup().getName());
//        List<ConsumerRecord<String, String>> rec = events.records(0);
//        List records = events.records();

//        for (Object event : records) {
//            try {
        System.out.println("event -> " + event.offset());
        System.out.println("event -> " + event.value());
//            } catch (Exception ex) {
//                throw new RuntimeException("");
//            }
    }
}
