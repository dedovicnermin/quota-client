package io.nermdev.kafka.quota_client.clients.producer;

import io.nermdev.kafka.quota_client.StatsPrinter;
import io.nermdev.kafka.quota_client.framework.exception.ClientConfigException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Properties;

import static io.nermdev.kafka.quota_client.framework.config.ConfigUtils.getProperties;

public final class QuotaProducerSample {
  public static void main(String[] args) throws ClientConfigException {
    final Properties properties = getProperties(args);
    final String topic = (String) properties.getOrDefault("topic", "volume-test");

    try (var producer = new KafkaProducer<String, String>(properties)) {
      final var statsPrinter = new StatsPrinter();

      final var key = "some_key";
      final var value = "some_value".repeat(1000);

      while (true) {
        final Callback callback = (metadata, exception) -> {
          statsPrinter.accumulateRecord();
          if (exception != null) exception.printStackTrace();
        };

        producer.send(new ProducerRecord<>(topic, key, value), callback);
        statsPrinter.maybePrintStats();
      }
    }
  }
}
