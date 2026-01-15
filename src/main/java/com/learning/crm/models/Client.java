package com.learning.crm.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Client(
        @Id
        String id,
        String name,
        String email,
        String passwordHash
) {}
