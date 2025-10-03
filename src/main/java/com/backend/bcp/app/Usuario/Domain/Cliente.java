package com.backend.bcp.app.Usuario.Domain;

import com.backend.bcp.shared.Domain.Usuario;

public class Cliente extends Usuario {
    public Cliente(Long id, String nombre, String contrasena, String correo,
                   String dni, String direccion, String telefono) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono);
    }
    
}
