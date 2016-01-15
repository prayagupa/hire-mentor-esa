package hireartists.consumer;

import hireartists.domain.ArtistHiredEvent;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by prayagupd
 * on 11/16/15.
 */

@Component
public class HireArtistMessageListener extends MultiEventListener {

    public void handleEventType1(ArtistHiredEvent event) {
        System.out.println("saving type1");
    }

    public void handleEventType2(TestEvent event) {
        System.out.println("saving type2");
    }

    class TestEvent {

    }
}
