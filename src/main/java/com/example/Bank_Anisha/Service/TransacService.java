package com.example.Bank_Anisha.Service;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.Entity.TransactionEntity;
import com.example.Bank_Anisha.Entity.TransactionType;
import com.example.Bank_Anisha.Mapper.TransactionMapper;
import com.example.Bank_Anisha.dto.TransactionDto;
import com.example.Bank_Anisha.repository.BankRepository;
import com.example.Bank_Anisha.repository.TransactionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

//import static com.example.Bank_Anisha.Entity.TransactionType.Deposit;

@Service
public class TransacService {
    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    BankRepository bankRepository;
    private static final double MIN_DEPOSIT = 100.0;
    private static final double MIN_WITHDRAW = 500.0;

    @Transactional
    public TransactionEntity withdraw(Double amount, Long accountId) {
        Account account = bankRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getStatus().equalsIgnoreCase("DEACTIVATE")) {
            throw new RuntimeException("Account is deactivated. Deposits not allowed.");
        }
        if (account.isDeleted()) {
            throw new RuntimeException("Account is deleted. Transactions not allowed.");
        }
        if(account.getBalance()< amount){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Amount should be less than balance");
        }
        

        account.setBalance(account.getBalance()-amount);

        TransactionEntity tran = TransactionEntity.builder()
                .account(account)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .transactionType(TransactionType.Withdraw)
                .build();
        transactionRepo.save(tran);
        bankRepository.save(account);
        return tran;
    }
    @Transactional
    public TransactionEntity Deposit(Double amount, Long accountId){
        Account account= bankRepository.findById(accountId)
                .orElseThrow(()->new RuntimeException("account not found"));

        if (account.getStatus().equalsIgnoreCase("DEACTIVATE")) {
            throw new RuntimeException("Account is deactivated. Deposits not allowed.");
        }
        if (account.isDeleted()) {
            throw new RuntimeException("Account is deleted. Transactions not allowed.");
        }
        if(amount<100){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"amount should be more than 100 ");
        }
        account.setBalance(account.getBalance()+amount);

        TransactionEntity transaction = TransactionEntity.builder()
                .account(account)
                .timestamp(LocalDateTime.now())
                .transactionType(TransactionType.Deposit)
                .amount(amount)
                .build();
        transactionRepo.save(transaction);
        bankRepository.save(account);
        return transaction;


    }

    public TransactionDto DebitAmounts(Long id1, Long id2, Double amount) {
        Account fromAccount = bankRepository
                .findById(id1)
                .orElseThrow(() -> new RuntimeException("FromAccount not exists"));
        Account toAccount = bankRepository.findById(id2)
                .orElseThrow(() -> new RuntimeException("ToAccount not exists"));

        Double sharedBalance= fromAccount.getBalance();
        if(sharedBalance<amount){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient Balance");
        }
        toAccount.setBalance(toAccount.getBalance()+amount);
        fromAccount.setBalance(fromAccount.getBalance()-amount);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(amount)
                .transactionType(TransactionType.Debit)
                .timestamp(LocalDateTime.now())
                .account(fromAccount)
                .build();
        transactionRepo.save(entity);
        bankRepository.save(fromAccount);
        bankRepository.save(toAccount);
        TransactionDto dto = TransactionMapper.mapToTransactionDto(entity);
        return dto;

    }
    public TransactionDto CreditAmounts(Long id1, Long id2, Double amount) {
        Account fromAccount = bankRepository
                .findById(id2)
                .orElseThrow(() -> new RuntimeException("FromAccount not exists"));
        Account toAccount = bankRepository.findById(id1)
                .orElseThrow(() -> new RuntimeException("ToAccount not exists"));

        Double sharedBalance= fromAccount.getBalance();
        if(sharedBalance<amount){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient Balance");
        }
        toAccount.setBalance(toAccount.getBalance()+amount);
        fromAccount.setBalance(fromAccount.getBalance()-amount);

        TransactionEntity entity = TransactionEntity.builder()
                .amount(amount)
                .transactionType(TransactionType.Credit)
                .timestamp(LocalDateTime.now())
                .account(fromAccount)
                .build();
        transactionRepo.save(entity);
        bankRepository.save(fromAccount);
        bankRepository.save(toAccount);
        TransactionDto dto = TransactionMapper.mapToTransactionDto(entity);
        return dto;

    }
}
