package com.backend.bcp.shared.Infraestructure.Security;

import java.util.Date;

import com.backend.bcp.shared.Aplication.Security.TokenService;
import com.backend.bcp.shared.Domain.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtService implements TokenService {
    final String secret = "ItsASecret";
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
    public boolean validToken(String token) {
        try{
            final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public String getUser(String token) {
        
    }
    

}
