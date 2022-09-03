package com.example.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity extends BaseTimeEntity{



    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

/*    @PrePersist
    public void prePersis(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now; //값을 채워야 쿼리 작업시 편하다
    }

    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }*/
}
