package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("WithdrawalTransaction")
public class WithdrawalTransaction extends Transaction{

  public WithdrawalTransaction(double amount) {
    super(amount);
  }

  @Override
  public void post(Account account) {
    try{
      account.withdraw(this.getAmount());
    } catch (Exception ex){
      throw new InsufficientBalanceException();
    }
    account.getTransactions().add(this);
  }
}
