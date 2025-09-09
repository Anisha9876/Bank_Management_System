package com.example.Bank_Anisha.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity
{
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updateAt;

    public LocalDateTime getCreatedAt() {
        return createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updateAt;
    }
}
