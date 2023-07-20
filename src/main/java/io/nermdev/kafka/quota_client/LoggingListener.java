package io.nermdev.kafka.quota_client;


import io.nermdev.kafka.quota_client.framework.ReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class LoggingListener implements Consumer<ReceiveEvent> {
    static final Logger log = LoggerFactory.getLogger(LoggingListener.class);
    @Override
    public void accept(ReceiveEvent event) {
        log.info("[{}_{}_{}] {} : {}", event.getPartition(),event.getOffset(), event.getTimestamp(), event.getKey().toString(), event.getPayload().toString());
    }
}
