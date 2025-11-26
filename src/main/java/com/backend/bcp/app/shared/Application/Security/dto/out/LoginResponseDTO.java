package com.backend.bcp.app.shared.Application.Security.dto.out;

public class LoginResponseDTO {
    private String nombre;
    private String token;
    private String tipoUsuario;
    private String dni;
    public LoginResponseDTO(String nombre, String token,String tipoUsuario, String dni) {
        this.nombre = nombre;
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.dni = dni;
    }
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
}
