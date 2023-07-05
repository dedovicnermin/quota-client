package io.nermdev.kafka.quota_client;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public final class QuotaProducerSample {
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
              .withCustomEntry(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,
              600_000);
    } else {
      config = new MTLSProducerConfig()
              .withBootstrapServers(properties.getProperty("bootstrap.servers"))
              .withKeystoreLocation(properties.getProperty("keystore.location"))
              .withClientId(properties.getProperty("client.id"))
              .withTruststoreLocation(properties.getProperty("truststore.location"))
              .withCustomEntry(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,
                      600_000);
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

        producer.send(new ProducerRecord<>(topic, key, value), callback);
        statsPrinter.maybePrintStats();
      }
    }
  }
}
