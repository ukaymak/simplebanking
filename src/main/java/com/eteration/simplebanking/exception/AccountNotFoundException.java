package com.eteration.simplebanking.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException() {
    super("Account Not Found!");
  }
}
