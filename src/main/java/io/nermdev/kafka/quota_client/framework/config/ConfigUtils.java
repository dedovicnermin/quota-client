package io.nermdev.kafka.quota_client.framework.config;



import io.nermdev.kafka.quota_client.framework.exception.ClientConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigUtils {
    private ConfigUtils() {}

    public static void loadConfig(final String file, final Properties properties) throws ClientConfigException {
        try (
                final FileInputStream inputStream = new FileInputStream(file)
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new ClientConfigException("Could not map client config to property object. Ensure properties file referenced is valid.", e.getCause());
        }
    }

    

    public static Properties getProperties(String[] args) throws ClientConfigException {
        if (args.length < 1) throw new IllegalArgumentException("Pass path to application.properties");
        final Properties properties = new Properties();
        ConfigUtils.loadConfig(args[0], properties);
        return properties;
    }
}
