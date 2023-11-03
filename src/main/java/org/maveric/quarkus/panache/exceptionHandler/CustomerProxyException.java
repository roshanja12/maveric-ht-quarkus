package org.maveric.quarkus.panache.exceptionHandler;

public class CustomerProxyException extends Exception{
    public CustomerProxyException() {
        super();
    }

    public CustomerProxyException(String message) {
        super(message);
    }

    public CustomerProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerProxyException(Throwable cause) {
        super(cause);
    }

    protected CustomerProxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
