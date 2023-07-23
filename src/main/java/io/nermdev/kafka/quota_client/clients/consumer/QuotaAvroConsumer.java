package io.nermdev.kafka.quota_client.clients.consumer;

import io.nermdev.kafka.quota_client.StatsPrinter;
import io.nermdev.kafka.quota_client.framework.exception.ClientConfigException;
import io.nermdev.kafka.quota_client.framework.listener.LoggingListener;
import io.nermdev.kafka.quota_client.framework.config.ConfigUtils;
import io.nermdev.kafka.quota_client.framework.receiver.BaseReceiver;
import io.nermdev.kafka.quota_client.framework.ConsumerCloser;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class QuotaAvroConsumer extends BaseReceiver<GenericRecord, GenericRecord> {
    private final KafkaConsumer<GenericRecord, GenericRecord> consumer;
    static final Logger log = LoggerFactory.getLogger(QuotaAvroConsumer.class);

    protected QuotaAvroConsumer(Properties config) {
        super(config);
        consumer = new KafkaConsumer<>(consumerConfig);
    }



    @Override
    public void run() {
        final StatsPrinter statsPrinter = new StatsPrinter();
        consumer.subscribe(Collections.singleton(topic));
        try {
            while (true) {
                final ConsumerRecords<GenericRecord, GenericRecord> records = consumer.poll(Duration.ofSeconds(1));
                for (var record : records) {
                    fire(record);
                    statsPrinter.accumulateRecord();
                }
                consumer.commitAsync();
                statsPrinter.maybePrintStats();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ERROR : {}", e.getMessage());
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

    @Override
    protected KafkaConsumer<GenericRecord, GenericRecord> getConsumer() {
        return consumer;
    }
    @Override
    protected Logger getLogger() { return log; }

    public static void main(String[] args) throws ClientConfigException {
        final Properties properties = ConfigUtils.getProperties(args);
        final QuotaAvroConsumer quotaAvroConsumer = new QuotaAvroConsumer(properties);
        quotaAvroConsumer.addListener(new LoggingListener<>());
        new Thread(quotaAvroConsumer).start();
        Runtime.getRuntime().addShutdownHook(new Thread(new ConsumerCloser<>(quotaAvroConsumer)));
    }
}
