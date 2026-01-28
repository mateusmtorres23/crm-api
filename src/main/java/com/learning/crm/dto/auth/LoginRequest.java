package com.learning.crm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @NotEmpty(message = "Email is required")
        @Email
        String email,
        @NotEmpty(message = "Password is required")
        String password

) {}
