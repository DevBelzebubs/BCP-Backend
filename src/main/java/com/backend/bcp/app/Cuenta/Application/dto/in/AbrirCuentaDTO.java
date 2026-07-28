package com.backend.bcp.app.Cuenta.Application.dto.in;

public record AbrirCuentaDTO(
    String nombre,
    String correo,
    String dni,
    String telefono,
    String contrasena,
    String direccion,
    String tipoCuenta
) {}
