package com.learning.crm.security;

import lombok.Builder;

@Builder
public record JWTUserData(

        String userId,
        String email

) {}