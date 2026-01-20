package com.learning.crm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateClientRequest(

        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        @Email
        String email

) {}
