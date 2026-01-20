package com.learning.crm.controller;

import com.learning.crm.dto.request.LoginRequest;
import com.learning.crm.dto.request.RegisterRequest;
import com.learning.crm.dto.response.LoginResponse;
import com.learning.crm.dto.response.RegisterResponse;
import com.learning.crm.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public  AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {

        try {
            authService.userRegister(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        try{
            LoginResponse response = authService.userLogin(request);
            return ResponseEntity.ok(response.token());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}
