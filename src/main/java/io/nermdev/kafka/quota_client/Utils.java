package io.nermdev.kafka.quota_client;

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
}
