package com.backend.bcp.shared.Infraestructure.Security;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.backend.bcp.shared.Aplication.Security.ports.out.TokenService;
import com.backend.bcp.shared.Domain.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements TokenService {
    final SecretKey secret = JwtConfig.SECRET_KEY;
    @Override
    public String generateToken(Usuario user) {
        final long expiration = 3600000;
        return Jwts.builder()
            .subject(user.getNombre())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    @Override
    public boolean validToken(String token, Usuario user) {
        try{
            final Claims claims = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
            final String username = getUser(token);
            return username.equals(user.getNombre()) && !claims.getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public String getUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    return claims.getSubject();     
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }
}