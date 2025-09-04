package com.example.Bank_Anisha.repository;

import com.example.Bank_Anisha.Entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<TransactionEntity,Long> {

    List<TransactionEntity> findByAccountIdOrderByTimestampDesc(Long accountId);
}
