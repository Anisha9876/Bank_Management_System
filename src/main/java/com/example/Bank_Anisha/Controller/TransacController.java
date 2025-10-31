package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.Entity.TransactionEntity;
import com.example.Bank_Anisha.Mapper.TransactionMapper;
import com.example.Bank_Anisha.Service.TransacService;
import com.example.Bank_Anisha.dto.API_Response;
import com.example.Bank_Anisha.dto.TransactionDto;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction/")
public class TransacController {

    private TransacService service;

    public TransacController(TransacService service) {
        this.service = service;
    }
    @PutMapping("{accountId}/withdraw")
    @CachePut(value = "bank",key="#accountId")
   public ResponseEntity<TransactionDto> withdraw(@RequestParam Double amount
            ,@PathVariable Long accountId){
       TransactionEntity transaction = service.withdraw(amount, accountId);
       TransactionDto dto = TransactionMapper.mapToTransactionDto(transaction);
       return ResponseEntity.ok(dto);
   }
   @PutMapping("{accountId}/deposit")
   @CachePut(value = "bank",key="#accountId")
    public ResponseEntity<TransactionDto> Deposit(@PathVariable Long accountId,
                                                  @RequestParam Double amount){
       TransactionEntity transaction = service.Deposit(amount, accountId);
       TransactionDto dto = TransactionMapper.mapToTransactionDto(transaction);
       return ResponseEntity.ok(dto);
   }

   @PutMapping("Debit/{id1}/{id2}/{amount}")
   @CachePut(value = "bank",key="#id2")
    public ResponseEntity<TransactionDto> DebitAmount(@PathVariable Long id1
           , @PathVariable Long id2
           , @PathVariable Double amount
           ){
       TransactionDto debitDto = service.DebitAmounts(id1, id2, amount);

       return ResponseEntity.ok(debitDto);


   }
    @PutMapping("Credit/{id1}/{id2}/{amount}")
    @CachePut(value = "bank",key="#id1")
    public ResponseEntity<TransactionDto> CreditAmount(@PathVariable Long id1
            , @PathVariable Long id2
            , @PathVariable Double amount){
        TransactionDto creditDto = service.CreditAmounts(id1, id2, amount);
        return ResponseEntity.ok(creditDto);

    }
    @GetMapping("transaction/{id}")
    @Cacheable(value = "bank",key="#id")
    public ResponseEntity<API_Response<List<TransactionDto>>> getTransactions(@PathVariable Long id
            , @RequestParam Integer page
            , @RequestParam Integer size){
        List<TransactionDto> account = service.getAllTransactions(size, page, id);
        if(account.isEmpty()){
            return ResponseEntity.ok(new API_Response<>(account,"No Transaction"));
        }
        return ResponseEntity.ok(new API_Response<>(account,"success"));

    }


}
