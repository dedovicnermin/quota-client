package io.nermdev.kafka.quota_client.framework.exception;

public class ClientInterruptedException extends RuntimeException{
    public ClientInterruptedException(String message) {super(message);}
    public ClientInterruptedException(String message, Throwable cause) {super(message, cause);}
    public ClientInterruptedException(Throwable cause) {super(cause);}
}
