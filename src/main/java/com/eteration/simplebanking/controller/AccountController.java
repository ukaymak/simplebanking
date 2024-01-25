package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.request.AccountRequest;
import com.eteration.simplebanking.model.request.TransactionalRequest;
import com.eteration.simplebanking.model.response.BaseResponse;
import com.eteration.simplebanking.model.response.TransactionalStatus;
import com.eteration.simplebanking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account/v1")
public class AccountController {

  private final AccountService accountService;

  public AccountController(
      AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping(value = "/create")
  public ResponseEntity<BaseResponse<Account>> createAccount(@RequestBody AccountRequest request) {
    return ResponseEntity.ok(new BaseResponse<>(accountService.createAccount(request)));
  }
  
  @GetMapping(value = "/{accountNumber}")
  public ResponseEntity<BaseResponse<Account>> getAccount(@PathVariable String accountNumber) {
    return ResponseEntity.ok(new BaseResponse<>(accountService.findAccount(accountNumber)));
  }

  @PostMapping("/credit/{accountNumber}")
  ResponseEntity<BaseResponse<TransactionalStatus>> credit(@PathVariable String accountNumber, @RequestBody TransactionalRequest request) {
     return ResponseEntity.ok(new BaseResponse<>(accountService.credit(accountNumber, request.getAmount())));
  }

  @PostMapping("/debit/{accountNumber}")
  ResponseEntity<BaseResponse<TransactionalStatus>> debit(@PathVariable String accountNumber, @RequestBody TransactionalRequest request) {
    return ResponseEntity.ok(new BaseResponse<>(accountService.debit(accountNumber, request.getAmount())));
  }
}
