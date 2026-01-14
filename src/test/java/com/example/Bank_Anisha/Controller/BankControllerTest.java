package com.example.Bank_Anisha.Controller;

import com.example.Bank_Anisha.dto.API_Response;
import com.example.Bank_Anisha.dto.AccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class BankControllerTest {
    @Autowired
    BankController bankController;


    @Test
    public void addAccountTest(){
        AccountDto accountDto=new AccountDto();
        accountDto.setAccountHolderName("Sampad");
        accountDto.setPassword("samp12");
        accountDto.setBalance(23000);

        ResponseEntity<?> response = bankController.addAccount(accountDto);
        assertNotNull(response);
    }
}
