package com.hireartists.consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by prayagupd
 * on 11/23/15.
 */

public abstract class KafkaMessageListener  extends Thread {

    private Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    protected String ARTISTS_GROUP = "artists_group";
    public static final String ZOOK_2181 = "localhost:2181";
    protected String TOPIC = "topic_artists";

    private ConsumerConnector consumerConnector;

    KafkaMessageListener(){
        Properties properties = new Properties(){{
            put("zookeeper.connect", ZOOK_2181);
            put("group.id", ARTISTS_GROUP);
        }};
        consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
    }
    @Override
    public void run() {
        System.out.println("Before KafkaMessageListener " + this.getThreadGroup().getName());

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>(){{
            put(TOPIC, new Integer(1));
        }};
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        logger.info("1 getting topic stream from " + TOPIC + " -> " + consumerMap.size() + " -> " + consumerMap.get(TOPIC).size());
        List<KafkaStream<byte[], byte[]>> kafkaStreamList = consumerMap.get(TOPIC);
        KafkaStream<byte[], byte[]> topicStream =  kafkaStreamList.get(0);
        ConsumerIterator<byte[], byte[]> it = topicStream.iterator();

        onMessage(it);
        System.out.println("After KafkaMessageListener " + this.getThreadGroup().getName());
    }

    public abstract void onMessage(ConsumerIterator<byte[], byte[]> streams);
}
