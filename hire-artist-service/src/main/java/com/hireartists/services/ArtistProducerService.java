package com.hireartists.services;

import com.hireartists.domains.Artist;
import com.hireartists.repository.ArtistRepository;import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class ArtistProducerService {

    public static final String DEFAULT_ZOOK = "127.0.0.1:2181";
    public static final String DEFAULT_BROKER = "127.0.0.1:9092";

    Logger logger = LoggerFactory.getLogger(ArtistProducerService.class);

    @Autowired
    public ArtistRepository artistRepository;
    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private final String topic = "topic.artists";

    public ArtistProducerService() {
//        artistRepository = new ArtistRepository();
        Properties config = new Properties() {{
            put("bootstrap.servers", "localhost:9092");
            //put("group.id", "artist_group");
            put("enable.auto.commit", "true");
            put("auto.commit.interval.ms", "1000");
            put("session.timeout.ms", "30000");
            put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            put("partition.assignment.strategy", "range");
        }};
        producer = new KafkaProducer<>(config);
    }

    public void produce(String message) {
        //send a message
        System.out.println("producing");
        producer.send(new ProducerRecord<String, String>(topic, "", message.toString()));
    }

    public List<Artist> search(String criteria) {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .filter(artist -> artist.getName().startsWith(criteria))
                .collect(Collectors.toList());
    }

    public List<Json> convertToJson(String criteria) {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(artist -> new Json("name", artist.getName()))
                .collect(Collectors.toList());
    }

    public Map<String, List<Artist>> groupListByType(String criteria) {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .collect(Collectors.groupingBy(artist -> artist.getType()));
    }

    private class Json {
        private String key;
        private String value;

        public Json(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @PostConstruct
    public void init() {
        logger.info("initializing ArtistEventListener " + new Date());
//        new ArtistEventListener().start();
    }
}
