package com.backend.bcp.shared.Infraestructure.entity;

import org.springframework.boot.context.properties.bind.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Usuario")
    private Long id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Contrasena")
    private String contrasena;
    @Column(name = "Correo")
    private String correo;
    @Column(name = "DNI")
    private String dni;
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Telefono")
    private String telefono;
    public UsuarioEntity(Long id, String nombre, String contrasena, String correo, String dni, String direccion,
            String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    
}
