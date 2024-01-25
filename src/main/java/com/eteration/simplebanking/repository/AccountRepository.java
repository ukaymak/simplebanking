package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

  Optional<Account> findBankAccountByAccountNumber(String accountNumber);
}
