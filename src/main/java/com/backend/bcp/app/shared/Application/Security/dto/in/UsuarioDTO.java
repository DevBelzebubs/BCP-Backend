package com.backend.bcp.app.shared.Application.Security.dto.in;

public record UsuarioDTO(Long id,
    String nombre,
    String contrasena,
    String correo,
    String dni,
    String direccion,
    String telefono) {

}
