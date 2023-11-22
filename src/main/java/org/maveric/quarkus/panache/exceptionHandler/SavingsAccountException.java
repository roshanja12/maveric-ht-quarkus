package org.maveric.quarkus.panache.exceptionHandler;

public class SavingsAccountException extends RuntimeException{
//  public SavingsAccountException() {
//    super();
//  }

  public SavingsAccountException(String message) {
    super(message);
  }

//  public SavingsAccountException(String message, Throwable cause) {
//    super(message, cause);
//  }
//
//  public SavingsAccountException(Throwable cause) {
//    super(cause);
//  }
//
//  protected SavingsAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//    super(message, cause, enableSuppression, writableStackTrace);
//  }
}
