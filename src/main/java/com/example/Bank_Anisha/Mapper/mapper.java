package com.example.Bank_Anisha.Mapper;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.dto.AccountDto;

import java.util.ArrayList;

public class mapper {
    public static Account mapToAccount(AccountDto accountDto){
        Account account=new Account();
               account.setId(accountDto.getId());
                account.setAccountHolderName(accountDto.getAccountHolderName());
                account.setBalance(accountDto.getBalance());
                account.setStatus(accountDto.getStatus());
                account.setDeleted(accountDto.isDeleted());
                account.setPassword(accountDto.getPassword());
                return account;
    }
    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto=new AccountDto(
        account.getId(),
        account.getAccountHolderName(),
       account.getPassword(),
         account.getBalance(),
         account.getStatus(),
          account.isDeleted(),
          account.getTransactions()

        );
        return accountDto;
    }
}
