package io.nermdev.kafka.quota_client.clients.producer;

import static io.nermdev.kafka.quota_client.framework.config.ConfigUtils.getProperties;

import io.nermdev.kafka.quota_client.StatsPrinter;
import io.nermdev.kafka.quota_client.framework.exception.ClientConfigException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Properties;

public final class BufferedQuotaProducerSample {
  static final Logger log = LoggerFactory.getLogger(BufferedQuotaProducerSample.class);
  public static void main(String[] args) throws ClientConfigException {
    final Properties properties = getProperties(args);
    final String topic = (String) properties.getOrDefault("topic", "volume-test");

    try (var producer = new KafkaProducer<String, String>(properties)) {
      final var statsPrinter = new StatsPrinter();

      final String key = "some_key";
      final String value = "some_value".repeat(1000);

      while (true) {
        final Callback callback = (metadata, exception) -> {
          statsPrinter.accumulateRecord();
          if (exception != null) exception.printStackTrace();
        };

        final var record = new ProducerRecord<>(topic, key, value);
        final var tookMs = timed(() -> producer.send(record, callback));
        final String format = String.format("Blocked for %,d ms", tookMs);
        log.info(format);
        statsPrinter.maybePrintStats();
      }
    }
  }


  private static long timed(Runnable task) {
    final var start = System.currentTimeMillis();
    task.run();
    return System.currentTimeMillis() - start;
  }
}
