package hireartists.consumer;

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
@Scope("prototype")
public class HireArtistMessageListener extends Thread {

    public static final String DEFAULT_ZK = "192.168.86.5:2181";
    public static final String DEFAULT_CONSUMER_NAME = "group_artists";
    private final String TOPIC = "topic.artists";

    private Logger loggerNotFkingWorking = LoggerFactory.getLogger(HireArtistMessageListener.class);

    private ConsumerConnector consumerConnector;

    public HireArtistMessageListener(){

        Properties properties = new Properties(){{
            put("zookeeper.connect", DEFAULT_ZK);
            put("group.id", DEFAULT_CONSUMER_NAME);
        }};
        consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }
    @Override
    public void run() {
        consume();
    }

    public void consume(){
        loggerNotFkingWorking.info("consuming");
        consumeMessageStreams();
    }

    private void consumeMessageStreams() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>(){{
            put(TOPIC, new Integer(1));
        }};
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
                consumerConnector.createMessageStreams(topicCountMap);
        loggerNotFkingWorking.info("getting topic stream from {}", TOPIC);
        KafkaStream<byte[], byte[]> topicStream =  consumerMap.get(TOPIC).get(0);
        ConsumerIterator<byte[], byte[]> it = topicStream.iterator();
        while(it.hasNext()) {
            System.out.println(new String(it.next().message()));
            loggerNotFkingWorking.info(new String(it.next().message()));
        }
    }
}
