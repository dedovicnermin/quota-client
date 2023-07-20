package io.nermdev.kafka.quota_client.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractReceiver<K, V> implements EventReceiver<K, V>, Runnable{

    private final List<Consumer<ReceiveEvent>> listeners = new ArrayList<>();


    @Override
    public void addListener(Consumer<ReceiveEvent> listener) {
        listeners.add(listener);
    }


    protected final void fire(final ReceiveEvent event) {
        for (var listener : listeners) {
            listener.accept(event);
        }
    }



}
