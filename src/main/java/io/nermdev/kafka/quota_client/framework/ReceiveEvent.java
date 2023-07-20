package io.nermdev.kafka.quota_client.framework;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiveEvent {
    private final String key;
    private final String payload;
    private final Integer partition;
    private final Long offset;
    private String timestamp;
}
