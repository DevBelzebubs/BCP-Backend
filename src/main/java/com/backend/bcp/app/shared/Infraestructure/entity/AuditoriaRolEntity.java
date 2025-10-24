package com.backend.bcp.app.Shared.Infraestructure.entity;

import java.time.LocalDateTime;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Auditoria_Rol")
public class AuditoriaRolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoriaId")
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fechaHoraCambio;

    @Column(nullable = false)
    private Long adminId;

    @Column(nullable = false)
    private Long empleadoId;

    @Column
    private TipoRol rolAnterior;

    @Column(nullable = false)
    private TipoRol rolNuevo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraCambio() {
        return fechaHoraCambio;
    }

    public void setFechaHoraCambio(LocalDateTime fechaHoraCambio) {
        this.fechaHoraCambio = fechaHoraCambio;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public TipoRol getRolAnterior() {
        return rolAnterior;
    }

    public void setRolAnterior(TipoRol rolAnterior) {
        this.rolAnterior = rolAnterior;
    }

    public TipoRol getRolNuevo() {
        return rolNuevo;
    }

    public void setRolNuevo(TipoRol rolNuevo) {
        this.rolNuevo = rolNuevo;
    }

    
}
