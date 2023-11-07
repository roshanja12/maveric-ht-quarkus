package org.maveric.quarkus.panache.exceptionHandler;


public class SavingDetailsNotFoundException extends RuntimeException{
    public SavingDetailsNotFoundException(String message) {
        super(message);
    }
}
