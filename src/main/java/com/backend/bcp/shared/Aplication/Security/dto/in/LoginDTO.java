package com.backend.bcp.shared.Aplication.Security.dto.in;

public class LoginDTO {
    private String nombre;
    private String contrasena;
    
    public LoginDTO() {
    }
    
    public LoginDTO(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
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
}