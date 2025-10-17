package com.backend.bcp.app.shared.Application.Security.dto.in;

public class LoginDTO {
    private String nombre;
    private String contrasena;
    private String tipoUsuario;
    
    public LoginDTO() {
    }
    
    public LoginDTO(String nombre, String contrasena, String tipoUsuario) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}