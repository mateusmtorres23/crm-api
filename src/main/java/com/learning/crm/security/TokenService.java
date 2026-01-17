package com.learning.crm.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    @Value("${api.security.jwt.secret-key}")
    private String secret;

}
