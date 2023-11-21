package org.maveric.quarkus.panache.exceptionHandler;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String message) {
        super(message);
    }
}
