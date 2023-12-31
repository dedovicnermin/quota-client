package io.nermdev.kafka.quota_client.framework;



import io.nermdev.kafka.quota_client.framework.receiver.EventReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerCloser<K, V> implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ConsumerCloser.class);

    private final EventReceiver<K, V> consumerDemoWorker;

    public ConsumerCloser(final EventReceiver<K, V> consumerDemoWorker) {
        this.consumerDemoWorker = consumerDemoWorker;
    }

    @Override
    public void run() {
        try {
            consumerDemoWorker.close();
        } catch (Exception e) {
            log.error("ISSUES WITH CLOSING CONSUMER : {}", e.getMessage());
        }
    }

}
