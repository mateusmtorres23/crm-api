package com.learning.crm.controller;

import com.learning.crm.dto.auth.LoginRequest;
import com.learning.crm.dto.auth.RegisterRequest;
import com.learning.crm.dto.auth.LoginResponse;
import com.learning.crm.dto.auth.RegisterResponse;
import com.learning.crm.dto.client.response.CreateClientResponse;
import com.learning.crm.models.User;
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
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
        RegisterResponse newUser = authService.userRegister(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.userLogin(request);

        return ResponseEntity.ok().body((response));
    }
}
