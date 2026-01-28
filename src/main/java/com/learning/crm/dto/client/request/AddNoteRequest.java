package com.learning.crm.dto.request;

import jakarta.validation.constraints.NotBlank;


public record AddNoteRequest (

        @NotBlank String content

) {}
