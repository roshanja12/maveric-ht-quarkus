package org.maveric.quarkus.panache.exceptionHandler;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
