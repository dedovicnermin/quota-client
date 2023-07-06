package io.nermdev.kafka.quota_client.clients;

import io.nermdev.kafka.quota_client.StatsPrinter;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

import static io.nermdev.kafka.quota_client.Utils.getProperties;

public final class QuotaProducerSample {
  public static void main(String[] args) {
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
