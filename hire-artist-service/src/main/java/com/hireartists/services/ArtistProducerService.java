package com.hireartists.services;

import com.hireartists.domains.Artist;
import com.hireartists.repository.ArtistRepository;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import kafka.javaapi.producer.Producer;
import org.springframework.stereotype.Service;

/**
 * Created by prayagupd
 * on 10/31/15.
 */

@Service
public class ArtistProducerService {

    @Autowired
    public ArtistRepository artistRepository;
    private Producer<String, String> producer;
    private final String topic = "artists-topic";

    public ArtistProducerService(){
        artistRepository = new ArtistRepository();
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
}
