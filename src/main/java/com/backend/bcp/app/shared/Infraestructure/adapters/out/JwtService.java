package com.backend.bcp.app.Shared.Infraestructure.adapters.out;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Shared.Application.Security.ports.out.TokenService;
import com.backend.bcp.app.Shared.Domain.Usuario;
import com.backend.bcp.app.Shared.Infraestructure.Security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService implements TokenService {
    private final JwtConfig jwtConfig;
    
    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    @Override
    public String generateToken(Usuario user,String tipoUsuario) {
        final long expiration = 3600000;
        final SecretKey secret = jwtConfig.getSecretKey();
        System.out.println("➡️  Firmando token... Usando clave con HashCode: " + secret.hashCode());
        return Jwts.builder()
            .subject(user.getNombre())
            .claim("tipoUsuario", tipoUsuario)
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(secret)
            .compact();
    }
    @Override
    public boolean validToken(String token, Usuario user) {
        try{
            final String username = getUser(token);
            final Claims claims = getClaims(token);
            return username.equals(user.getNombre()) && !claims.getExpiration().before(new Date());
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String getUser(String token) {
        return getClaims(token).getSubject();     
    }

    @Override
    public Claims getClaims(String token) {
        SecretKey secretKey = jwtConfig.getSecretKey();
        System.out.println("⬅️  Verificando token... Usando clave con HashCode: " + secretKey.hashCode());
        return Jwts.parser()
                   .verifyWith(secretKey)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }
}