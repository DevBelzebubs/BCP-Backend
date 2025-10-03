package com.backend.bcp.shared.Aplication.Security;

public interface TokenService {
    String generarToken(String usuarioId);
    boolean validarToken(String token);
    String obtenerUsuario(String token);
}
