package com.hireartists.services;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.utils.Json;
import org.springframework.stereotype.Component;
import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import org.springframework.stereotype.Service;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class ArtistProducerService {

    private Producer<String, String> producer;
    private final String topic = "artists-topic";

    public ArtistProducerService(){
        ProducerConfig config = new ProducerConfig(new Properties(){{
                put("serializer.class", "kafka.serializer.StringEncoder");
                put("zk.connect", "127.0.0.1:2181");
                put("metadata.broker.list", "localhost:9092");
            }});
        producer = new Producer<String, String>(config);
    }

    public void produce(String message){
        //send a message
        KeyedMessage<String, String> kafkaMessage = new KeyedMessage<String, String>(topic,message.toString());
        producer.send(kafkaMessage);
    }
}
