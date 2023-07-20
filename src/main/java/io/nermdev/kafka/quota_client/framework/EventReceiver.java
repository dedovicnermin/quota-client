package io.nermdev.kafka.quota_client.framework;

import java.io.Closeable;
import java.util.function.Consumer;

public interface EventReceiver<K, V> extends Closeable {
    void addListener(Consumer<ReceiveEvent> listener);
    void start();
    void close();
}
