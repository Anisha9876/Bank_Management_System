package com.example.Bank_Anisha.dto;

import com.example.Bank_Anisha.Entity.TransactionEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;
    @NotBlank(message = "Account holder name cannot be blank")
    private String accountHolderName;
    private String password;
    @NotNull(message = "Balance is required")
    @Min(value = 100, message = "Balance must be greater than or equal to 100")
    private double balance;
    private String status;
    private boolean isDeleted;
    private final Integer minimumBalance=1000;
    private List<TransactionEntity> transactions;

}
