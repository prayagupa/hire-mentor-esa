package com.hireartists;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

public class EventReader {

    public static final String EVENTSTREAM = "eventstream";

    static KafkaConsumer consumer = new KafkaConsumer(new Properties() {{
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        put(ConsumerConfig.GROUP_ID_CONFIG, EventReader.class.getSimpleName());
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    }});

    static KafkaConsumer consumer2 = new KafkaConsumer(new Properties() {{
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        put(ConsumerConfig.GROUP_ID_CONFIG, EventReader.class.getSimpleName() + "-seek");
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    }});

    public static void main(String[] args) {

        seekStream(Instant.now().minus(60, MINUTES));

        //

    }

    private static void readNormally() {

        consumer.subscribe(Arrays.asList(EVENTSTREAM));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }

    private static void seek() {

        boolean seeking = true;

        while (seeking) {
            consumer2.subscribe(Arrays.asList(EVENTSTREAM));

            Map<TopicPartition, Long> seekQuery = getSeekAllPartitionsQuery(consumer2, Instant.now().minus(60, MINUTES));

            Map<TopicPartition, OffsetAndTimestamp> offsetsForEachPartition = consumer2.offsetsForTimes(seekQuery);

            if (offsetsForEachPartition == null) {
                System.out.println("result: " + offsetsForEachPartition);

            } else {
                ConsumerRecords<String, String> records = consumer2.poll(100);
                offsetsForEachPartition.entrySet()
                        .stream()
                        .forEach(entry -> consumer2.seek(entry.getKey(), entry.getValue().offset()));

                for (ConsumerRecord<String, String> record : records)
                    System.out.printf(
                            "partition = %s, " +
                                    "offset = %d, " +
                                    "key = %s, " +
                                    "value = %s%n",
                            record.partition(),
                            record.offset(), record.key(), record.value());
            }
        }
    }

    private static void seekStream(Instant from) {

        boolean seeking = true;

        Map<TopicPartition, Long> seekQuery = getSeekAllPartitionsQuery(
                consumer2,
                from
        );

        Map<TopicPartition, OffsetAndTimestamp> offsetsForEachPartition = consumer2.offsetsForTimes(seekQuery);

        if (offsetsForEachPartition == null) {
            System.out.println("result: " + offsetsForEachPartition);

        } else {
            List<PartitionInfo> list = consumer2.partitionsFor(EVENTSTREAM);
            consumer2.assign(
                    list
                            .stream().map((PartitionInfo $) -> new TopicPartition(EVENTSTREAM, $.partition()))
                            .collect(Collectors.toList())
            );

            ConsumerRecords<String, String> records = consumer2.poll(100);

            offsetsForEachPartition.entrySet()
                    .stream()
                    .forEach(entry -> consumer2.seek(entry.getKey(), entry.getValue().offset()));

            for (ConsumerRecord<String, String> record : records)
                System.out.printf(
                        "partition = %s, " +
                                "offset = %d, " +
                                "key = %s, " +
                                "value = %s%n",
                        record.partition(),
                        record.offset(), record.key(), record.value());
        }
    }

    private static Map<TopicPartition, Long> getSeekAllPartitionsQuery(KafkaConsumer reader,
                                                                       Instant from) {
        List<PartitionInfo> list = reader.partitionsFor(EVENTSTREAM);

        List<TopicPartition> ps = list.stream()
                .map(p -> new TopicPartition(EVENTSTREAM, p.partition()))
                .collect(Collectors.toList());

        Map<TopicPartition, Long> seekQuery = new HashMap<>();
        long start = from.toEpochMilli();

        ps.forEach(p -> {
            seekQuery.put(p, start);
        });

        return seekQuery;
    }

}
