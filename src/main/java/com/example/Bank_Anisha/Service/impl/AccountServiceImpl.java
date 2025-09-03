package com.example.Bank_Anisha.Service.impl;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.Mapper.mapper;
import com.example.Bank_Anisha.Service.BankService;
import com.example.Bank_Anisha.dto.AccountDto;
import com.example.Bank_Anisha.repository.BankRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements BankService {
    private BankRepository bankRepository;

    public AccountServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= mapper.mapToAccount(accountDto);
        Account save = bankRepository.save(account);
        return mapper.mapToAccountDto(save);

    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=bankRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));
        return mapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=bankRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));
        if (account.getStatus().equalsIgnoreCase("DEACTIVATE")) {
            throw new RuntimeException("Account is deactivated. Deposits not allowed.");
        }
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = bankRepository.save(account);
        return mapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=bankRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }
        if (account.getStatus().equalsIgnoreCase("DEACTIVATE")) {
            throw new RuntimeException("Account is deactivated. Deposits not allowed.");
        }
        double total= account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = bankRepository.save(account);
        return mapper.mapToAccountDto(savedAccount);

    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = bankRepository.findByDeletedFalse();
        return accounts.stream().map((account) -> mapper.mapToAccountDto(account))
                .collect(toList());
    }

  //  @Override
//    public void deleteAccount(Long id) {
//        Account account=bankRepository
//                .findByIdAndDeletedFalse(id)
//                .orElseThrow(()-> new RuntimeException("Account does not exists"));
//        bankRepository.deleteById(id);
//    }

//    @Override
//    public Page<AccountDto> getAllByAccountHolderName(String accountHolderName, int page,int size ){
//        Pageable p= PageRequest.of(
//                page,size, Sort.by("accountHolderName").ascending());
//        Page<Account> allByName = bankRepository.findByAccountHolderNameContainingIgnoreCase(accountHolderName, p);
//        return allByName.map(account -> new AccountDto(
//                account.getId(),
//                account.getAccountHolderName(),
//                account.getBalance()
//        ));
//    }

    //custom pagination
    private final Map<Integer, Integer> pageSizeHistory = new HashMap<>();
    @Override
    public List<AccountDto> getAllByAccountHolderName(String accountHolderName, int page, int size) {
        pageSizeHistory.put(page, size);

        List<Account> allAccounts = bankRepository.findByAccountHolderNameContainingIgnoreCaseAndDeletedFalse(accountHolderName,Sort.by("id").ascending());

        int startIndex = 0;
        for (int i = 0; i < page; i++) {
            startIndex += pageSizeHistory.getOrDefault(i, 0); // if previous page not yet requested, treat as 0
        }

        int endIndex = Math.min(startIndex + size, allAccounts.size());

        if (startIndex >= allAccounts.size()) {
            return List.of(); // No data left
        }

        List<Account> pageAccounts = allAccounts.subList(startIndex, endIndex);

        return pageAccounts.stream()
                .map(acc -> mapper.mapToAccountDto(acc))
                .toList();

}

public List<Account> getAllAccountsByBalance(double c1,double c2){
    List<Account> byBalanceBetween = bankRepository.findByBalanceBetweenAndDeletedFalse(c1, c2);

    return byBalanceBetween;
}

@Override
public Account activatedAccount(Long id, String status){
    Account account = bankRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Account not exists"));
    account.setStatus(status);
    return bankRepository.save(account);

}
@Override
public Account deactivatedAccount(Long id, String status){
    Account account = bankRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Account not exists"));
    account.setStatus(status);
    return bankRepository.save(account);
}

@Override
    public Account isDeletedById(long id){
    Account account = bankRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Account not exists"));

    account.setDeleted(true);
    Account acc = bankRepository.save(account);
    return acc;
}

}
