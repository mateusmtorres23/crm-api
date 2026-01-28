package com.learning.crm.dto.client.request;

import jakarta.validation.constraints.NotBlank;


public record AddNoteRequest (

        @NotBlank String content

) {}
