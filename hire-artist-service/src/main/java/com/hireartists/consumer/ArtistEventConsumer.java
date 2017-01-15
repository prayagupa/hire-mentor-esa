package com.hireartists.consumer;

import com.hireartists.driver.EventConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
public class ArtistEventConsumer extends EventConsumer {

    private Logger logger = LoggerFactory.getLogger(ArtistEventConsumer.class);

    public ArtistEventConsumer() {
        super();
        TOPIC = "topic.artists";
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> event) {
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("Start " + new Date() + "========== consuming ============= {} "
                + this.getThreadGroup().getName());
        System.out.println("eventOffset -> " + event.offset());
        System.out.println("eventPayload -> " + event.value());
        System.out.println("=============================================================");
        System.out.println("=============================================================");
    }
}
