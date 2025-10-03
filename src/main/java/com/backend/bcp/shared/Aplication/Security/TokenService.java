package com.backend.bcp.shared.Aplication.Security;

import com.backend.bcp.shared.Domain.Usuario;

public interface TokenService {
    String generateToken(Usuario user);
    boolean validToken(String token);
    String getUser(String token);
}
