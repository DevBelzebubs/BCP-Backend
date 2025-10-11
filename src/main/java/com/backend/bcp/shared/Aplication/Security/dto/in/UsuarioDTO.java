package com.backend.bcp.shared.Aplication.Security.dto.in;

public record UsuarioDTO(Long id,
    String nombre,
    String contrasena,
    String correo,
    String dni,
    String direccion,
    String telefono) {

}
