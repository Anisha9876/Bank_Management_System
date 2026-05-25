package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.Entity.TransactionEntity;
import com.example.Bank_Anisha.Mapper.TransactionMapper;
import com.example.Bank_Anisha.Service.TransacService;
import com.example.Bank_Anisha.dto.API_Response;
import com.example.Bank_Anisha.dto.TransactionDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransacController {

    private TransacService service;

    public TransacController(TransacService service) {
        this.service = service;
    }

    // Transfer: debit from id1, credit to id2
    @PutMapping("/transfer")
    @CacheEvict(value = "bank", allEntries = true)
    public ResponseEntity<API_Response<TransactionDto>> transfer(
            @RequestBody Map<String, Object> request) {

        Long fromId = Long.valueOf(request.get("fromAccountId").toString());
        Long toId = Long.valueOf(request.get("toAccountId").toString());
        Double amount = Double.valueOf(request.get("amount").toString());

        TransactionDto dto = service.DebitAmounts(fromId, toId, amount);
        return ResponseEntity.ok(new API_Response<>("success",
                "Transfer successful", dto));
    }

    // Get transaction history
    @GetMapping("/{id}")
    @Cacheable(value = "bank", key="#id")
    public ResponseEntity<API_Response<List<TransactionDto>>> getTransactions(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<TransactionDto> transactions =
                service.getAllTransactions(size, page, id);

        if (transactions.isEmpty()) {
            return ResponseEntity.ok(new API_Response<>("success",
                    "No transactions found", transactions));
        }
        return ResponseEntity.ok(new API_Response<>("success",
                "Transactions fetched successfully", transactions));
    }
}