package com.learning.crm.controller;

import com.learning.crm.dto.request.LoginRequest;
import com.learning.crm.dto.request.RegisterRequest;
import com.learning.crm.dto.response.LoginResponse;
import com.learning.crm.dto.response.RegisterResponse;
import com.learning.crm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<RegisterResponse> registerUser() {



        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login() {



        return null;
    }

}
