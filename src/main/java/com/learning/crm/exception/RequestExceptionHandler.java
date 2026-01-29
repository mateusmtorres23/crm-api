package com.learning.crm.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleEntityNotFound(HttpServletRequest request, EntityNotFoundException e) {

        ExceptionDTO errorDetails = new ExceptionDTO(
                java.time.Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Entity Not Found",
                e.getMessage(),
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public  ResponseEntity<ExceptionDTO> handleBadRequest(HttpServletRequest request, IllegalArgumentException e) {

        ExceptionDTO errorDetails = new ExceptionDTO(
                java.time.Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Operation",
                e.getMessage(),
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(IllegalStateException.class)
    public  ResponseEntity<ExceptionDTO> handleBusinessConflict(HttpServletRequest request, IllegalStateException e) {

        ExceptionDTO errorDetails = new ExceptionDTO(
                java.time.Instant.now(),
                HttpStatus.CONFLICT.value(),
                "Resource Conflict",
                e.getMessage(),
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public  ResponseEntity<ExceptionDTO> handleAuthenticationErrors(HttpServletRequest request, BadCredentialsException  e) {

        ExceptionDTO errorDetails = new ExceptionDTO(
                java.time.Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid Credentials",
                e.getMessage(),
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ExceptionDTO> handleValidationErrors(HttpServletRequest request, MethodArgumentNotValidException  e) {

        ExceptionDTO errorDetails = new ExceptionDTO(
                java.time.Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                e.getBindingResult().getFieldError().getDefaultMessage(),
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ExceptionDTO> handleGeneralErrors(HttpServletRequest request, Exception  e) {

        ExceptionDTO errorDetails = new ExceptionDTO(
                java.time.Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected internal error occurred.",
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

}
