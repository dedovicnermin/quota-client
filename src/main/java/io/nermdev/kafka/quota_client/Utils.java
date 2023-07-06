package io.nermdev.kafka.quota_client;

import io.nermdev.kafka.quota_client.config.AbstractClientConfig;
import io.nermdev.kafka.quota_client.config.MTLSProducerConfig;
import io.nermdev.kafka.quota_client.config.SaslProducerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Utils {
    private Utils() {}

    public static void loadConfig(final String file, final Properties properties) {
        try (
                final FileInputStream inputStream = new FileInputStream(file)
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    

    public static Properties getProperties(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException("Pass path to application.properties");
        final Properties properties = new Properties();
        Utils.loadConfig(args[0], properties);
        return properties;
    }
}
