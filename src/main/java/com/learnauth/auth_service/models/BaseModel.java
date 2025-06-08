package com.learnauth.auth_service.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass

@Getter
@Setter
public class BaseModel    {

    @Id  // annotation makes the rid property as primary key in the table
    @GeneratedValue(strategy= GenerationType.IDENTITY)  // Identity means auto-increment
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // this annotation tells spring boot about the formats of Date object to be stored.
    @CreatedDate  // this annotation tells spring that only handle it for object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date updatedAt;
}
