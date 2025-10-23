package com.backend.bcp.app.Shared.Application.Security.dto.in;

public class EmpleadoDTO {
    private String nombre;
    private String contrasena;
    private String correo;
    private String dni;
    private String direccion;
    private String telefono;
    private double salario;
    public EmpleadoDTO() {
    }
    
    public EmpleadoDTO(String nombre, String contrasena, String correo, String dni, String direccion, String telefono,
            double salario) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.salario = salario;
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
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }
    
}
