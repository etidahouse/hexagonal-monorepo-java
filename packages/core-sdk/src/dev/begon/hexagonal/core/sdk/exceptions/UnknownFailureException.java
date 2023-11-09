package dev.begon.hexagonal.core.sdk.exceptions;

public class UnknownFailureException extends Exception {

    public UnknownFailureException(String message) {
        super(message);
    }

    public UnknownFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
