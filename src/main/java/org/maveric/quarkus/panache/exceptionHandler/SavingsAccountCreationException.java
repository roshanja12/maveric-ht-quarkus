package org.maveric.quarkus.panache.exceptionHandler;

public class SavingsAccountCreationException extends RuntimeException{
    public SavingsAccountCreationException() {
        super();
    }

    public SavingsAccountCreationException(String message) {
        super(message);
    }

    public SavingsAccountCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavingsAccountCreationException(Throwable cause) {
        super(cause);
    }

    protected SavingsAccountCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
