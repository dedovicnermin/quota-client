package io.nermdev.kafka.quota_client.framework.listener;


import io.nermdev.kafka.quota_client.DateFormatter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class LoggingListener<K, V> implements Consumer<ConsumerRecord<K, V>> {
    static final Logger log = LoggerFactory.getLogger(LoggingListener.class);
    static final String TIMEZONE = "CST";

    @Override
    public void accept(final ConsumerRecord<K, V> event) {
        final String time = DateFormatter.formatDateToString(event.timestamp(), TIMEZONE);
        log.info(
                "[{}-{}_{}_{}] {} : {}",
                event.topic(),
                event.partition(),
                event.offset(),
                time,
                event.key(),
                event.value()
        );
    }
}
