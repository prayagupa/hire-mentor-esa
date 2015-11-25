package com.hireartists.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
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
        TOPIC = "topic_artists";
    }

    @Override
    public void onMessage(ConsumerIterator<byte[], byte[]> consumerIterator) {
        logger.info("Start " + new Date() + "========== consuming ============= {} " + this.getThreadGroup().getName());
        while(consumerIterator.hasNext()) {
            System.out.println(new String(consumerIterator.next().message()));
        }
    }
}
