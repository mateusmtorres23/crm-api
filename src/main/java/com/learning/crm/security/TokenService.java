package com.learning.crm.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.learning.crm.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${api.security.jwt.secret-key}")
    private String secret;

    public String generateToken(User user) {

        byte[] keyBytes = Base64.getDecoder().decode(secret);
        Algorithm algorithm = Algorithm.HMAC256(keyBytes);

        return JWT.create()
                .withClaim("userId", user.getId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .withIssuedAt(Instant.now())
                .sign(algorithm);

    }

    public Optional<JWTUserData> validateToken(String token) {

        try {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            Algorithm algorithm = Algorithm.HMAC256(keyBytes);
            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(JWT.decode(token).getClaim("userId").asString())
                    .email(JWT.decode(token).getSubject())
                    .build());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
