package io.nermdev.kafka.quota_client;

import static java.lang.System.*;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public final class BufferedQuotaProducerSample {
  public static void main(String[] args) throws InterruptedException {
    final var topic = "volume-test";
    if (args.length < 1) throw new IllegalArgumentException("Pass path to application.properties");
    final Properties properties = new Properties();
    Utils.loadConfig(args[0], properties);

    AbstractClientConfig<?> config;
    if (properties.getProperty("security.protocol").contains("SASL")) {
      config = new SaslProducerConfig()
              .withBootstrapServers(properties.getProperty("bootstrap.servers"))
              .withUsername(properties.getProperty("username"))
              .withPassword(properties.getProperty("password"))
              .withClientId(properties.getProperty("client.id"))
              .withTruststoreLocation(properties.getProperty("truststore.location"))
              .withCustomEntry(ProducerConfig.BUFFER_MEMORY_CONFIG,
                      100_000);
    } else {
      config = new MTLSProducerConfig()
              .withBootstrapServers(properties.getProperty("bootstrap.servers"))
              .withKeystoreLocation(properties.getProperty("keystore.location"))
              .withClientId(properties.getProperty("client.id"))
              .withTruststoreLocation(properties.getProperty("truststore.location"))
              .withCustomEntry(ProducerConfig.BUFFER_MEMORY_CONFIG,
                      100_000);
    }

    final var props = config.mapify();
    try (var producer = new KafkaProducer<String, String>(props)) {
      final var statsPrinter = new StatsPrinter();

      final var key = "some_key";
      final var value = "some_value".repeat(1000);

      while (true) {
        final Callback callback = (metadata, exception) -> {
          statsPrinter.accumulateRecord();
          if (exception != null) exception.printStackTrace();
        };

        final var record = new ProducerRecord<>(topic, key, value);
        final var tookMs = timed(() -> {
          producer.send(record, callback);
        });
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
