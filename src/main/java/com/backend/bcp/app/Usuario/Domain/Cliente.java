package com.backend.bcp.app.Usuario.Domain;

import java.time.LocalDate;

import com.backend.bcp.shared.Domain.Usuario;

public class Cliente extends Usuario {
    private LocalDate fechaRegistro;
    public Cliente(Long id, String nombre, String contrasena, String correo,
                   String dni, String direccion, String telefono) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono);
    }    
    public Cliente(){}
    public Cliente(Long id, String nombre,String contrasena, String correo,String dni,String direccion,String telefono, LocalDate fechaRegistro){

    }
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
