package hireartists.controller.hireartists.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class ArtistsConsumeService {

    private Logger logger = LoggerFactory.getLogger(ArtistsConsumeService.class);

    private ConsumerConnector consumerConnector;
    private final String TOPIC = "artists-topic";

    public ArtistsConsumeService(){
        Properties properties = new Properties(){{
            put("zookeeper.connect","localhost:2181");
            put("group.id","artists-test-group");
        }};
        consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }

    public void consume(){
        logger.info("consuming");
        consumeMessageStreams();
    }

    private void consumeMessageStreams() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>(){{
            put(TOPIC, new Integer(1));
        }};
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> topicStream =  consumerMap.get(TOPIC).get(0);
        ConsumerIterator<byte[], byte[]> it = topicStream.iterator();
        while(it.hasNext())
            logger.info(new String(it.next().message()));
    }

}
