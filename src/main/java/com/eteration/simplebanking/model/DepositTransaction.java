package com.eteration.simplebanking.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("DepositTransaction")
public class DepositTransaction extends Transaction{

  public DepositTransaction(double amount) {
    super(amount);
  }

  @Override
  public void post(Account account) {
    account.deposit(this.getAmount());
    account.getTransactions().add(this);
  }
}
