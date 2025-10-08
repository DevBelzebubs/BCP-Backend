package com.backend.bcp.shared.Aplication.Security.dto.out;

public class LoginResponseDTO {
    private String nombre;
    private String token;
    private String tipoUsuario;
    public LoginResponseDTO(String nombre, String token,String tipoUsuario) {
        this.nombre = nombre;
        this.token = token;
        this.tipoUsuario = tipoUsuario;
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
    
}
