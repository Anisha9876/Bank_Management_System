package com.example.Bank_Anisha.Service;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.dto.AccountDto;

import java.util.List;

public interface BankService {
   AccountDto createAccount(AccountDto accountDto);
   AccountDto getAccountById(Long id);

   AccountDto deposit(Long id ,double amount);

   AccountDto withdraw(Long id,double amount);

   List<AccountDto> getAllAccounts();

//   void deleteAccount(Long id);

   List<AccountDto> getAllByAccountHolderName(String accountHolderName, int page,int size);

   List<Account> getAllAccountsByBalance(double c1,double c2);

   Account activatedAccount(Long id, String status);

   Account deactivatedAccount(Long id, String status);

   Account isDeletedById(long id);
}
