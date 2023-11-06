package org.maveric.quarkus.panache.exceptionHandler;


public class SavingsAccountDetailsNotFoundException extends RuntimeException{
    public SavingsAccountDetailsNotFoundException(String message) {
        super(message);
    }
}
