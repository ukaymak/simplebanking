package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.service.AccountService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountService accountService;


  @Test
  void givenAccountNumber_DepositTransactionForExistAccount_thenReturnJson() {

    Account account = new Account("123456789", "unal", 150.0);
    when(accountRepository.findBankAccountByAccountNumber("123456789"))
        .thenReturn(Optional.of(account));
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    var result = accountService.credit("123456789", 1500.0);
    assertEquals("OK", result.getStatus());
    assertEquals(1650, account.getBalance());

  }

  @Test
  void givenAccountNumber_WithdrawalTransactionForExistAccountAndSufficientBalance_thenReturnJson() {

    Account account = new Account("123456789", "unal", 150.0);
    when(accountRepository.findBankAccountByAccountNumber("123456789"))
        .thenReturn(Optional.of(account));
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    var result = accountService.debit("123456789", 50.0);
    assertEquals("OK", result.getStatus());
    assertEquals(100.0, account.getBalance());
  }

  @Test
  void givenAccountNumber_GetAccount_thenReturnJson() {

    Account account = new Account("123456789", "unal", 150.0);
    when(accountRepository.findBankAccountByAccountNumber("123456789"))
        .thenReturn(Optional.of(account));

    var response = accountService.findAccount("123456789");

    assertEquals("123456789", response.getAccountNumber());
  }
}
