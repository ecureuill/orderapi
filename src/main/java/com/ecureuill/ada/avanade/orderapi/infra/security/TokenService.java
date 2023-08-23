package com.ecureuill.ada.avanade.orderapi.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ecureuill.ada.avanade.orderapi.domain.user.UserEntity;

@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                .withIssuer("orderapi")
                .withSubject(user.getUsername())
                .withExpiresAt(expireDate())
                .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error on generate jwt", e);
        }
    }

    public String getSubject(String token){
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                .withIssuer("orderapi")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Error on verify jwt", e);
        }
    }

    private Instant expireDate() {
        return LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.of("-03:00"));
    }
}
