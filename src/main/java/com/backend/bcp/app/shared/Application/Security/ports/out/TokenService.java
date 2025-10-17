package com.backend.bcp.app.shared.Application.Security.ports.out;

import com.backend.bcp.app.shared.Domain.Usuario;

import io.jsonwebtoken.Claims;

public interface TokenService {
    String generateToken(Usuario user, String tipoUsuario);
    boolean validToken(String token, Usuario user);
    String getUser(String token); 
    Claims getClaims(String token);
}
