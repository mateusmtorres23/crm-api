package com.learning.crm.dto.client.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EditClientRequest(

        String name,
        String phone,
        @Email
        String email

) {}
