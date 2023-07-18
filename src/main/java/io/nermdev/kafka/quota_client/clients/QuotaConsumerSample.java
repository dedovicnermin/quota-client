package io.nermdev.kafka.quota_client.clients;

import io.nermdev.kafka.quota_client.StatsPrinter;
import io.nermdev.kafka.quota_client.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

public class QuotaConsumerSample {

    static final Logger log = LoggerFactory.getLogger(QuotaConsumerSample.class);
    public static void main(String[] args) {
        final Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        final Properties properties = Utils.getProperties(args);
        final String topic = (String) properties.getOrDefault("topic", "volume-test");
        try (var consumer = new KafkaConsumer<String, String>(properties)) {
            final var statsPrinter = new StatsPrinter();
            consumer.subscribe(Collections.singleton(topic));
            try {
                while (true) {
                    final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                    if (!records.isEmpty()) {
                        for (ConsumerRecord<String, String> record : records) {
                            statsPrinter.accumulateRecord();
                            log.info("p_{} - o_{} - {}", record.partition(), record.offset(), format.format(new Date(record.timestamp())));
                        }
                        consumer.commitAsync();
                        statsPrinter.maybePrintStats();
                    }
                }
            } finally {
                consumer.close(Duration.ofSeconds(1));
            }
        }
    }
}
