package com.example.Bank_Anisha.repository;

import com.example.Bank_Anisha.Entity.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Account,Long> {
    List<Account> findByAccountHolderNameContainingIgnoreCaseAndDeletedFalse(String accountHolderName, Sort sort);

    List<Account> findByBalanceBetweenAndDeletedFalse(double count1,double count2);

    List<Account> findByDeletedFalse();

    Optional<Account> findByIdAndDeletedFalse(Long id);

    Account findByAccountHolderName(String username);
}

