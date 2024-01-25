package com.eteration.simplebanking.service;

import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.model.request.AccountRequest;
import com.eteration.simplebanking.model.response.TransactionalStatus;
import com.eteration.simplebanking.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

  public final String STATUS_OK = "OK";

  private final AccountRepository accountRepository;

  public AccountService(
      AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createAccount(AccountRequest request){

    Account account = new Account(request.getOwner());
    return accountRepository.save(account);
  }

  public Account findAccount(String accountNumber){

    var bankAccountOptional = this.accountRepository.findBankAccountByAccountNumber(accountNumber);
    if(bankAccountOptional.isEmpty()){
      throw new AccountNotFoundException();
    }
    return bankAccountOptional.get();
  }


  public TransactionalStatus credit(String accountNumber, double amount){

    var bankAccountOptional = this.accountRepository.findBankAccountByAccountNumber(accountNumber);
    if(bankAccountOptional.isEmpty()){
      throw new AccountNotFoundException();
    }

    var bankAccount = bankAccountOptional.get();
    Transaction transaction =  new DepositTransaction(amount);
    var approvalCode = transaction.getApprovalCode();
    bankAccount.post(transaction);
    accountRepository.save(bankAccount);

    return new TransactionalStatus(STATUS_OK, approvalCode);
  }

  public TransactionalStatus debit(String accountNumber, double amount){

    var bankAccountOptional = this.accountRepository.findBankAccountByAccountNumber(accountNumber);
    if(bankAccountOptional.isEmpty()){
      throw new AccountNotFoundException();
    }
    var bankAccount = bankAccountOptional.get();

    Transaction transaction =  new WithdrawalTransaction(amount);
    var approvalCode = transaction.getApprovalCode();
    bankAccount.post(transaction);
    accountRepository.save(bankAccount);

    return new TransactionalStatus(STATUS_OK, approvalCode);
  }
}
