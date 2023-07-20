package io.nermdev.kafka.quota_client.clients;

import io.nermdev.kafka.quota_client.DateFormatter;
import io.nermdev.kafka.quota_client.LoggingListener;
import io.nermdev.kafka.quota_client.Utils;
import io.nermdev.kafka.quota_client.framework.BaseReceiver;
import io.nermdev.kafka.quota_client.framework.ConsumerCloser;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

public class QuotaAvroConsumer extends BaseReceiver<GenericRecord, GenericRecord> {
    public static void main(String[] args) {
        final Properties properties = Utils.getProperties(args);
        final QuotaAvroConsumer quotaAvroConsumer = new QuotaAvroConsumer(properties);
        quotaAvroConsumer.addListener(new LoggingListener());
        new Thread(quotaAvroConsumer).start();
        Runtime.getRuntime().addShutdownHook(new Thread(new ConsumerCloser<>(quotaAvroConsumer)));
    }

    private final KafkaConsumer<GenericRecord, GenericRecord> consumer;
    static final Logger log = LoggerFactory.getLogger(QuotaAvroConsumer.class);

    protected QuotaAvroConsumer(Properties config) {
        super(config);
        consumer = new KafkaConsumer<>(consumerConfig);
    }

    @Override
    protected KafkaConsumer<GenericRecord, GenericRecord> getConsumer() {
        return consumer;
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singleton(topic));
        try {
            while (true) {
                final ConsumerRecords<GenericRecord, GenericRecord> records = consumer.poll(Duration.ofSeconds(1));
                for (var record : records) {
                    log.info("{} - [{}] - {} : {} " , DateFormatter.formatDateToString(new Date(record.timestamp()), "CST"), record.offset(), record.key(), record.value());
                }
                consumer.commitAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Something unexpected happened : {}", e.getMessage());
        } finally {
            try {
                consumer.commitSync();
            } finally {
                log.info("---Starting graceful closing of consumer---");
                consumer.close();
                countDownLatch.countDown();
                log.info("---Consumer gracefully closed---");
            }
        }
    }
}
