package com.example.Bank_Anisha.Mapper;

import com.example.Bank_Anisha.Entity.TransactionEntity;
import com.example.Bank_Anisha.dto.TransactionDto;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(TransactionEntity entity) {
        return TransactionDto.builder()
                .id(entity.getId())
                .transactionType(entity.getTransactionType())
                .amount(entity.getAmount())
                .accountId(entity.getAccount().getId())
                .timestamp(entity.getTimestamp())// again, if DTO expects AccountDto, map it
                .build();
    }
}
