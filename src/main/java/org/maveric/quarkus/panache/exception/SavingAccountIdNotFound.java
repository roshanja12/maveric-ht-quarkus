package org.maveric.quarkus.panache.exception;

public class SavingAccountIdNotFound extends RuntimeException{

    public SavingAccountIdNotFound(String message) {
        super(message);
    }
}
