package io.nermdev.kafka.quota_client.framework.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SecurityConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.*;

import java.util.Objects;

public final class SaslProducerConfig extends AbstractClientConfig<SaslProducerConfig> {
  private String bootstrapServers;
  
  private String username;
  
  private String password;

  private String truststoreLocation;
  
  private String clientId = "";



  public SaslProducerConfig withBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
    return this;
  }
  
  public SaslProducerConfig withUsername(String username) {
    this.username = username;
    return this;
  }

  public SaslProducerConfig withPassword(String password) {
    this.password = password;
    return this;
  }

  public SaslProducerConfig withClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public SaslProducerConfig withTruststoreLocation(String location) {
    this.truststoreLocation = location;
    return this;
  }

  @Override
  protected Class<?>[] getValidationClasses() {
    return new Class<?>[] {
      CommonClientConfigs.class,
      ProducerConfig.class,
      SecurityConfig.class,
      SaslConfigs.class,
      SslConfigs.class
    };
  }

  @Override
  protected void appendExpectedEntries(ExpectedEntryAppender expectedEntries) {
    expectedEntries.append(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    expectedEntries.append(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    expectedEntries.append(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
    expectedEntries.append(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "https");
    expectedEntries.append(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, this.truststoreLocation);
    expectedEntries.append(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "mystorepassword");
    expectedEntries.append(SaslConfigs.SASL_MECHANISM, "PLAIN");
    
    Objects.requireNonNull(bootstrapServers, "Bootstrap servers not set");

    
    Objects.requireNonNull(username, "Username not set");
    Objects.requireNonNull(password, "Password not set");
    final var loginModuleClass = PlainLoginModule.class.getName();
    final var saslJaasConfig = loginModuleClass 
        + " required\n"
        + "username=\"" + username + "\"\n"
        + "password=\""+ password + "\";";

    expectedEntries.append(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
    
    Objects.requireNonNull(clientId, "Client ID not set");
    expectedEntries.append(ProducerConfig.CLIENT_ID_CONFIG, clientId);
  }
}
