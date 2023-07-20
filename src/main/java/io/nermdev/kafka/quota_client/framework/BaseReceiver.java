package io.nermdev.kafka.quota_client.framework;


import org.apache.kafka.clients.consumer.KafkaConsumer;
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


    @Override
    public void start() {
        run();
    }

    @Override
    public void close() {
        System.out.println("BaseReceiver.close() invoked. Waking up consumer");
        getConsumer().wakeup();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.printf("Interrupted exception catch block (EXCEPTION) : %s", e.getMessage());
            System.err.println();
            throw new RuntimeException("Interrupt occurred during receiver shutdown \n",e);
        }
        System.out.println("Receiver and consumer closed");
    }




}
