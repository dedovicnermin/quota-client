package io.nermdev.kafka.quota_client.framework.receiver;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.Closeable;
import java.util.function.Consumer;

public interface EventReceiver<K, V> extends Closeable {
    void addListener(Consumer<ConsumerRecord<K, V>> listener);
    void start();
    void close();
}
