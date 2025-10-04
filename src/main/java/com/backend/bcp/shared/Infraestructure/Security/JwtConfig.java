package com.backend.bcp.shared.Infraestructure.Security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class JwtConfig {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final String CONTENT_TYPE = "application/json";
}
