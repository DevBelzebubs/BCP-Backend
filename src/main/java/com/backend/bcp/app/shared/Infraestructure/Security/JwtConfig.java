package com.backend.bcp.app.shared.Infraestructure.Security;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtConfig {
    @Value("${jwt.secret.key}")
    private String secretBase64; 

    public SecretKey SECRET_KEY;
    @PostConstruct
    public void afterPropertiesSet() {
        if (secretBase64 == null || secretBase64.isEmpty()) {
            throw new IllegalArgumentException("La clave secreta JWT no puede ser nula.");
        }
        byte[] keyBytes = Base64.getDecoder().decode(secretBase64);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes); 
        System.out.println("✅ Clave secreta JWT CREADA en JwtConfig. HashCode: " + this.SECRET_KEY.hashCode());
    }
    public SecretKey getSecretKey() {
        return SECRET_KEY;
    }
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String CONTENT_TYPE = "application/json";
}
