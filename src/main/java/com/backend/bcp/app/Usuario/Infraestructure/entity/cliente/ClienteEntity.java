package com.backend.bcp.app.Usuario.Infraestructure.entity.cliente;

import java.time.LocalDate;

import com.backend.bcp.app.shared.Infraestructure.entity.UsuarioEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cliente")
    private Long idCliente;
    @Column(name = "Fecha_Registro")
    private LocalDate fechaRegistro;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Usuario_ID_Usuario", referencedColumnName = "ID_Usuario")
    private UsuarioEntity idUsuario;
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public UsuarioEntity getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(UsuarioEntity idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    
}
