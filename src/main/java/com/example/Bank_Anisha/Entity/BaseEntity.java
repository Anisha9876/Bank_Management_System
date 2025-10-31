package com.example.Bank_Anisha.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity
{
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Column(name = "last_interest_applied")
    private LocalDateTime lastInterestAppliedDate;


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public LocalDateTime getLastInterestAppliedDate() {
        return lastInterestAppliedDate;
    }

    public void setLastInterestAppliedDate(LocalDateTime lastInterestAppliedDate) {
        this.lastInterestAppliedDate = lastInterestAppliedDate;
    }
}
