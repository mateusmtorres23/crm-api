package com.learning.crm.exception;

import java.time.Instant;

public record ExceptionDTO(

        Instant timestamp,
        int status,
        String errorTitle,
        String message,
        String uri

) {}
