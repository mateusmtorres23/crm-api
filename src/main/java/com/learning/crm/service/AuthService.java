package com.learning.crm.service;

import com.learning.crm.dto.request.LoginRequest;
import com.learning.crm.dto.request.RegisterRequest;
import com.learning.crm.dto.response.LoginResponse;
import com.learning.crm.dto.response.RegisterResponse;
import com.learning.crm.models.User;
import com.learning.crm.repository.UserRepository;
import com.learning.crm.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {

        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;

    }

    public RegisterResponse userRegister(RegisterRequest request) {

        userRepository.findUserByEmail(request.email()).ifPresent(user -> {
            throw new IllegalStateException("Email already in use");
        });

        String passwordHash = passwordEncoder.encode(request.password());

        User newUser = new User(
                null,
                request.email(),
                passwordHash
        );

        userRepository.save(newUser);

        return new RegisterResponse(request.email());

    }

    public LoginResponse userLogin(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found after successful authentication."));

        String token = tokenService.generateToken(user);

        return new LoginResponse(token);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
