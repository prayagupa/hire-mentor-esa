package com.hireartists.services;

import com.hireartists.consumer.ArtistEventListener;
import com.hireartists.consumer.KafkaMessageListener;
import com.hireartists.domains.Artist;
import com.hireartists.repository.ArtistRepository;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import kafka.javaapi.producer.Producer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class ArtistProducerService {

    public static final String DEFAULT_ZOOK = "192.168.86.5:2181";
    public static final String DEFAULT_BROKER = "192.168.86.10:9092";

    Logger logger = LoggerFactory.getLogger(ArtistProducerService.class);

    @Autowired
    public ArtistRepository artistRepository;
    private Producer<String, String> producer;
    private final String topic = "topic.artists";

    public ArtistProducerService(){
        artistRepository = new ArtistRepository();
        ProducerConfig config = new ProducerConfig(new Properties(){{
                put("serializer.class", "kafka.serializer.StringEncoder");
                put("zk.connect", DEFAULT_ZOOK);
                put("metadata.broker.list", DEFAULT_BROKER);
            }});
        producer = new Producer<String, String>(config);
    }

    public void produce(String message){
        //send a message
        KeyedMessage<String, String> kafkaMessage = new KeyedMessage<String, String>(topic,message.toString());
        producer.send(kafkaMessage);
    }

    public List<Artist> search(String criteria){
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .filter(artist -> artist.getName().startsWith(criteria))
                .collect(Collectors.toList());
    }

    public List<Json> convertToJson(String criteria){
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(artist -> new Json("name", artist.getName()))
                .collect(Collectors.toList());
    }

    public Map<String, List<Artist>> groupListByType(String criteria){
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .collect(Collectors.groupingBy(artist -> artist.getType()));
    }

    private class Json {
        private String key;
        private String value;

        public Json(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

    @PostConstruct
    public void init(){
        logger.info("initializing ArtistEventListener " + new Date());
        new ArtistEventListener().start();
    }
}
