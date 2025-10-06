package com.backend.bcp.app.Usuario.Infraestructure.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @Column(name = "ID_CLIENTE")
    private Long id;
    @Column(name = "Fecha_Registro")
    private LocalDate fechaRegistro;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Usuario_ID_Usuario", referencedColumnName = "ID_USUARIO")
    private Long idUsuario;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}
