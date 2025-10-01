package com.backend.bcp.aplicacion.ports.seguridad;

public interface TokenService {
    String generarToken(String usuarioId);
    boolean validarToken(String token);
    String obtenerUsuario(String token);
}
