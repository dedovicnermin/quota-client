package io.nermdev.kafka.quota_client.clients;

import static io.nermdev.kafka.quota_client.Utils.getProperties;
import static java.lang.System.*;

import io.nermdev.kafka.quota_client.StatsPrinter;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public final class BufferedQuotaProducerSample {
  public static void main(String[] args) {
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
        out.format("Blocked for %,d ms%n", tookMs);
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
