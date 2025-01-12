package com.duniasteak.service.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class IdentityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "CREATED_BY", updatable = false, columnDefinition = "varchar(1024) default '-'")
    private String createdBy;

    @Column(name = "UPDATED_BY", columnDefinition = "varchar(1024) default '-'")
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
