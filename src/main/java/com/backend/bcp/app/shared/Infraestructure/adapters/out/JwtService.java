package com.backend.bcp.app.shared.Infraestructure.adapters.out;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.shared.Application.Security.ports.out.TokenService;
import com.backend.bcp.app.shared.Domain.Usuario;
import com.backend.bcp.app.shared.Infraestructure.Security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements TokenService {
    final SecretKey secret = JwtConfig.SECRET_KEY;
    @Override
    public String generateToken(Usuario user,String tipoUsuario) {
        final long expiration = 3600000;
        return Jwts.builder()
            .subject(user.getNombre())
            .claim("tipoUsuario", tipoUsuario)
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