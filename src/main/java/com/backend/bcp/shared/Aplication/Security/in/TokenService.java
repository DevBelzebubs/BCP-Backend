package com.backend.bcp.shared.Aplication.Security.in;

import com.backend.bcp.shared.Domain.Usuario;

import io.jsonwebtoken.Claims;

public interface TokenService {
    String generateToken(Usuario user);
    boolean validToken(String token, Usuario user);
    String getUser(String token);
    Claims getClaims(String token);

}
