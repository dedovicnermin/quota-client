package io.nermdev.kafka.quota_client.framework.receiver;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractReceiver<K, V> implements EventReceiver<K, V>, Runnable{

    private final List<Consumer<ConsumerRecord<K, V>>> listeners = new ArrayList<>();

    @Override
    public void addListener(Consumer<ConsumerRecord<K, V>> listener) {
        listeners.add(listener);
    }

    protected final void fire(final ConsumerRecord<K, V> event) {
        for (var listener : listeners) {
            listener.accept(event);
        }
    }
}
