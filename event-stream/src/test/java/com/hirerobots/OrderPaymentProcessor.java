package com.hirerobots;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OrderPaymentProcessor {

    private static final String MAIN_TOPIC = "order-payment";
    private static final String RETRY_TOPIC = "order-payment-retry";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Producer properties
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

        // Example of sending a failed event with TTL
        String eventKey = "order123";
        String eventValue = "failed_event_data";
        long ttlMillis = 20000L;

        long eventExpirationTime = Instant.now().toEpochMilli() + ttlMillis;
        String payloadWithTTL = eventValue + "|" + eventExpirationTime;

        System.out.println("Sending failed event to Retry T");
        ProducerRecord<String, String> failedPayment = new ProducerRecord<>(RETRY_TOPIC, eventKey, payloadWithTTL);
        Future<RecordMetadata> sentRecord = producer.send(failedPayment);
        RecordMetadata recordMetadata = sentRecord.get();
        System.out.println("Sending failed event to Retry T completed " + recordMetadata.offset());

        // Consumer properties
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put("application.id", "order-payment-processor");
        consumerProps.put("default.key.serde", Serdes.String().getClass().getName());
        consumerProps.put("default.value.serde", Serdes.String().getClass().getName());

        // Read from the dead letter topic
        // Process the events and check for TTL expiration
        StreamsBuilder streamBuilder = new StreamsBuilder();
        KStream<String, String> deadLetterStream = streamBuilder.stream(RETRY_TOPIC);
        deadLetterStream.foreach((eKey, eventPayload) -> {
            System.out.println("Starting processing retry events");
            String[] parts = eventPayload.split("\\|");
            String eventData = parts[0];
            long expirationTime = Long.parseLong(parts[1]);

            if (Instant.now().toEpochMilli() >= expirationTime) {
                System.out.println("Forward the event to the main topic " + eventPayload);
                ProducerRecord<String, String> mainRecord = new ProducerRecord<>(MAIN_TOPIC, eKey, eventData);
                producer.send(mainRecord);
            }
        });

        Topology topology = streamBuilder.build();
        KafkaStreams streams = new KafkaStreams(topology, consumerProps);
        System.out.println("Starting Stream topology");
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
