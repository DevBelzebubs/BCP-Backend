package com.backend.bcp.app.shared.Infraestructure.Security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtConfig {
    @Value("${jwt.secret.key}")
    private String secretBase64; 

    public static SecretKey SECRET_KEY;

    public void afterPropertiesSet() {
        if (secretBase64 == null || secretBase64.isEmpty()) {
            throw new IllegalArgumentException("La clave secreta JWT no puede ser nula.");
        }
        SECRET_KEY = Keys.hmacShaKeyFor(secretBase64.getBytes());
    }
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String CONTENT_TYPE = "application/json";
}
