package com.eteration.simplebanking.exception;

public class InsufficientBalanceException  extends RuntimeException{

  public InsufficientBalanceException (){
    super("Insufficient Balance!");
  }
}
