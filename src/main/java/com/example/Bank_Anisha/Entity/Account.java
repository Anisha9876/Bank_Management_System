package com.example.Bank_Anisha.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
@Entity
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String accountHolderName;
    private double balance;
    private String status;
    private boolean deleted;
    private final Integer minimumBalance=1000;

    @OneToMany(mappedBy ="account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TransactionEntity> transactions = new ArrayList<>();


    }

