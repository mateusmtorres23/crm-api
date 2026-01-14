package com.learning.crm.models;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

@Entity
public record Client(
        @Id
        String id,
        String name,
        String email,
        String passwordHash
) {}
