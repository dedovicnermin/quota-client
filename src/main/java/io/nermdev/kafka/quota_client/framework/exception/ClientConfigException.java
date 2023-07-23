package io.nermdev.kafka.quota_client.framework.exception;

public class ClientConfigException extends Exception {
    public ClientConfigException(Throwable cause) {super(cause);}
    public ClientConfigException(String message) {
        super(message);
    }
    public ClientConfigException(String message, Throwable cause) {super(message, cause);}
}
