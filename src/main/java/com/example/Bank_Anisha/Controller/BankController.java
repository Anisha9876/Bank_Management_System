package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.Entity.TransactionEntity;
import com.example.Bank_Anisha.Exception.ResourceNotFoundException;
import com.example.Bank_Anisha.Mapper.TransactionMapper;
import com.example.Bank_Anisha.Mapper.mapper;
import com.example.Bank_Anisha.Service.BankService;
import com.example.Bank_Anisha.Service.TransacService;
import com.example.Bank_Anisha.dto.API_Response;
import com.example.Bank_Anisha.dto.AccountDto;
import com.example.Bank_Anisha.dto.TransactionDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class BankController {
    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    //add AccountREST API
    @PostMapping
    public ResponseEntity<API_Response<AccountDto>> addAccount(@Valid @RequestBody AccountDto accountDto){
        AccountDto createdAccount = bankService.createAccount(accountDto);
        return new ResponseEntity<>(new API_Response<>("success", "Account created successfully", createdAccount), HttpStatus.CREATED);
    }
    @Autowired
    private TransacService Transacservice;
    //deposite REST Api
    @PutMapping("/{id}/deposit")
    public ResponseEntity<API_Response<TransactionDto>> deposit(@PathVariable Long id
                                               , @RequestBody Map<String,Double> request){
        TransactionEntity transaction = Transacservice.withdraw(request.get("amount"), id);

        TransactionDto dto = TransactionMapper.mapToTransactionDto(transaction);
        return  ResponseEntity.ok(new API_Response<>("success", "Amount deposited successfully", dto));
    }
    //withdraw REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<API_Response<TransactionDto>> withdraw(
            @PathVariable Long id,
            @Valid @RequestBody Map<String,Double> request){

        Double amount = request.get("amount");
        TransactionEntity transaction = Transacservice.withdraw(amount, id);

        TransactionDto dto = TransactionMapper.mapToTransactionDto(transaction);

        return ResponseEntity.ok(new API_Response<>("success", "Amount withdraw successfully", dto));
    }
    //Get All Accounts REST API
    @GetMapping
    public ResponseEntity<API_Response<List<AccountDto>>> getAllAccounts(){
        List<AccountDto> allAccounts = bankService.getAllAccounts();
        return ResponseEntity.ok(new API_Response<>("success", "All accounts fetched", allAccounts));
    }
    //Delete Account Rest API
    // it is hard coded so we use soft delete below
//    @DeleteMapping("/{id}")
//    public ResponseEntity<API_Response<Object>> deleteAccount(@PathVariable Long id){
//        bankService.deleteAccount(id);
//        return  ResponseEntity.ok(new API_Response<>("success", "Account deleted successfully", null));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<API_Response<AccountDto>> getAccountsById(@PathVariable Long id) {
        AccountDto account = bankService.getAccountById(id);
        if (account == null) {
            throw new ResourceNotFoundException("Account with id " + id + " not found.");
        }
        API_Response<AccountDto> response = new API_Response<>(
                "success",
                "Account fetched successfully",
                account
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getAllByNames(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size,
                                                                        @RequestParam String accountHolderName

                              ){
        List<AccountDto> accounts = bankService.getAllByAccountHolderName(accountHolderName, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("accountHolderName", accountHolderName);
        response.put("currentPage", page);
        response.put("currentPageSize", size);
        response.put("data", accounts);
        response.put("message", accounts.isEmpty() ? "No more records" : "Data fetched successfully");

        return ResponseEntity.ok(response);


    }

    //getBank accounts by balance
    @GetMapping("/filter")
    public ResponseEntity<API_Response<AccountDto>> getAccountsBalance(@RequestParam double minBalance, @RequestParam double maxBalance){
        List<AccountDto> accounts = bankService.
                getAllAccountsByBalance(minBalance, maxBalance).stream().map(mapper::mapToAccountDto)
                .collect(Collectors.toList());


        return ResponseEntity.ok(new API_Response("success","get balance",accounts));
        
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<String> activate(@PathVariable long id, @RequestParam String status){
        if(status.equalsIgnoreCase("ACTIVATE")){
            Account account = bankService.activatedAccount(id, status);
            return ResponseEntity.ok("Account activated successfully");

        }
        else {
            Account account = bankService.deactivatedAccount(id, status);
            return ResponseEntity.ok("Account DeActivated successfully");
        }


    }
  @PutMapping("/delete/{id}")
    public ResponseEntity<API_Response<AccountDto>> deletedById(@PathVariable long id){
      Account acc = bankService.isDeletedById(id);
      AccountDto dto= mapper.mapToAccountDto(acc);
      API_Response<AccountDto> response= new API_Response<>("success","deleted",dto);

      return ResponseEntity.ok(response);
  }
}

