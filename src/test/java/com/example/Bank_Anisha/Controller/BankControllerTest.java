package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.Entity.Account;
import com.example.Bank_Anisha.Entity.TransactionEntity;
import com.example.Bank_Anisha.Service.BankService;
import com.example.Bank_Anisha.Service.TransacService;
import com.example.Bank_Anisha.dto.API_Response;
import com.example.Bank_Anisha.dto.AccountDto;
import com.example.Bank_Anisha.dto.TransactionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class BankControllerTest {
    @InjectMocks
    BankController bankController;

    @Mock
    BankService bankService;

    @Mock
    TransacService transService;

    @Test
    @Disabled
    public void addAccountTest(){
        AccountDto accountDto=new AccountDto();
        accountDto.setAccountHolderName("Sampad");
        accountDto.setPassword("samp12");
        accountDto.setBalance(23000);

        Mockito.when(bankService.createAccount(accountDto)).thenReturn(accountDto);
        ResponseEntity<API_Response<AccountDto>> response = bankController.addAccount(accountDto);
        assertNotNull(response);
        API_Response<AccountDto> body = response.getBody();
        AccountDto data = body.getData();
        Assertions.assertEquals(accountDto,data);
    }
    @Test
    public void deposit(){
        Account account=new Account();
        account.setAccountHolderName("Sampad");
        account.setPassword("samp12");
        account.setBalance(23000);

    TransactionEntity entity= new TransactionEntity();
        entity.setId(1L);
        entity.setAmount(2000d);
        entity.setTimestamp(LocalDateTime.now());
        entity.setAccount(account);

    Map<String,Double> map= new HashMap<>();
        map.put("amount",2000d);

    Mockito.when(transService.Deposit(2000d,1L)).thenReturn(entity);
        ResponseEntity<API_Response<TransactionDto>> depositAmount = bankController.deposit(1L, map);

    Assertions.assertEquals(entity.getAmount(),depositAmount.getBody().getData().getAmount());

    }

    @Test
    public void withdrawTest(){
        Account account1=new Account();
        account1.setPassword("2");
        account1.setId(2L);
        account1.setAccountHolderName("Pratik Parida");
        account1.setBalance(21000.0);

        TransactionEntity ent=new TransactionEntity();
        ent.setAccount(account1);
        ent.setAmount(2000.0);
        ent.setId(2L);
        Map<String,Double> map=new HashMap<>();
        map.put("amount",2000.0);

        Mockito.when(transService.withdraw(2000.0,2L)).thenReturn(ent);
        TransactionDto withdraw = bankController.withdraw(2L, map);
        Assertions.assertEquals(ent.getAmount(),withdraw.getAmount());
        Assertions.assertEquals(21000.0,withdraw.getAccount().getBalance());

    }
}
