package io.nermdev.kafka.quota_client.framework.receiver;


import io.nermdev.kafka.quota_client.framework.exception.ClientInterruptedException;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public abstract class BaseReceiver<K, V> extends AbstractReceiver<K, V> {
    protected final String topic;
    protected final CountDownLatch countDownLatch;
    protected final Properties consumerConfig;

    protected BaseReceiver(final Properties config) {
        consumerConfig = config;
        countDownLatch = new CountDownLatch(1);
        topic = (String) config.getOrDefault("topic", "volume-avro-test");
    }


    protected abstract KafkaConsumer<K, V> getConsumer();
    protected abstract Logger getLogger();


    @Override
    public void start() {
        run();
    }

    @Override
    public void close() {
        getLogger().info("BaseReceiver.close() invoked. Waking up consumer");
        getConsumer().wakeup();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            getLogger().error("Interrupted exception catch block (EXCEPTION) : {}", e.getMessage());
            throw new ClientInterruptedException("Interrupt occurred during receiver shutdown \n",e);
        }
        getLogger().info("Receiver and consumer closed");
    }
}
