package io.nermdev.kafka.quota_client.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SecurityConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;


public final class MTLSProducerConfig extends AbstractClientConfig<MTLSProducerConfig> {
  static final String PASS="mystorepassword";
  private String bootstrapServers;
  private String keystoreLocation;
  private String truststoreLocation;
  private String clientId = "";



  public MTLSProducerConfig withBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
    return this;
  }


  public MTLSProducerConfig withClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public MTLSProducerConfig withTruststoreLocation(String location) {
    this.truststoreLocation = location;
    return this;
  }

  public MTLSProducerConfig withKeystoreLocation(String location) {
    this.keystoreLocation = location;
    return this;
  }

  @Override
  protected Class<?>[] getValidationClasses() {
    return new Class<?>[] {
      CommonClientConfigs.class,
      ProducerConfig.class,
      SecurityConfig.class,
      SslConfigs.class

    };
  }

  @Override
  protected void appendExpectedEntries(ExpectedEntryAppender expectedEntries) {
    expectedEntries.append(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    expectedEntries.append(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    expectedEntries.append(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
    expectedEntries.append(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "https");
    expectedEntries.append(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, this.keystoreLocation);
    expectedEntries.append(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, PASS);
    expectedEntries.append(SslConfigs.SSL_KEY_PASSWORD_CONFIG, PASS);
    expectedEntries.append(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, this.truststoreLocation);
    expectedEntries.append(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, PASS);
    
    Objects.requireNonNull(bootstrapServers, "Bootstrap servers not set");
    expectedEntries.append(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

    
    Objects.requireNonNull(clientId, "Client ID not set");
    expectedEntries.append(ProducerConfig.CLIENT_ID_CONFIG, clientId);
  }
}
