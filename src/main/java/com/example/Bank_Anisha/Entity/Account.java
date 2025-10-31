package com.example.Bank_Anisha.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String accountHolderName;
    private String password;
    private double balance;
    private String status;
    private boolean deleted;
    private final Integer minimumBalance=1000;
    private Double rateOfInterest=5.0;
    private Double interestEarned= 0.0;

    @OneToMany(mappedBy ="account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TransactionEntity> transactions = new ArrayList<>();


    }

