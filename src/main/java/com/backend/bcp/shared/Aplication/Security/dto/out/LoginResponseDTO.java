package com.backend.bcp.shared.Aplication.Security.dto.out;

public class LoginResponseDTO {
    private String nombre;
    private String token;
    public LoginResponseDTO(String nombre, String token) {
        this.nombre = nombre;
        this.token = token;
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
    
}
