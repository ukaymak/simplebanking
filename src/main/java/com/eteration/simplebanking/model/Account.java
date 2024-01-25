package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.helper.SimpleBankingHelper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

  @Id
  @Column(length = 50)
  private String accountNumber;

  private String owner;

  private Double balance = 0.0;

  private Timestamp createDate = new Timestamp(System.currentTimeMillis());

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "accountNumber", referencedColumnName = "accountNumber")
  private List<Transaction> transactions = new ArrayList<>();

  public Account(String accountNumber, String owner, double balance) {
    this.accountNumber = accountNumber;
    this.owner = owner;
    this.balance = balance;
  }

  public Account(String owner) {
    this.owner = owner;
    this.setAccountNumber(SimpleBankingHelper.generateAccountNumber());
  }

  public Account(String owner,String accountNumber) {
    this.owner = owner;
    this.accountNumber = accountNumber;
  }

  public void post(Transaction transaction){
    transaction.post(this);
  }

  public void deposit(double amount) {
    setBalance(getBalance() + amount);
  }

  public void withdraw(double amount){
    if(this.balance < amount){
      throw new InsufficientBalanceException();
    }
    setBalance(getBalance() - amount);
  }
}
