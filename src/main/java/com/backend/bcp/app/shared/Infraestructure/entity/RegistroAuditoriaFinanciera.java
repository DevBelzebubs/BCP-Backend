package com.backend.bcp.app.Shared.Infraestructure.entity;

import java.time.LocalDateTime;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRegistroAuditoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Auditoria_Financiera")
public class RegistroAuditoriaFinanciera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Registro")
    private Long id;

    @Column(name = "FechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo_Registro", nullable = false)
    private TipoRegistroAuditoria tipoRegistro;

    @Column(name = "Descripcion", nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "ID_Usuario_Relacionado")
    private Long usuarioIdRelacionado; 

    @Column(name = "ID_Entidad_Relacionada")
    private Long entidadIdRelacionada;

    @Column(name = "Detalles_Adicionales", length = 2000)
    private String detallesAdicionales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public TipoRegistroAuditoria getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(TipoRegistroAuditoria tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getUsuarioIdRelacionado() {
        return usuarioIdRelacionado;
    }

    public void setUsuarioIdRelacionado(Long usuarioIdRelacionado) {
        this.usuarioIdRelacionado = usuarioIdRelacionado;
    }

    public Long getEntidadIdRelacionada() {
        return entidadIdRelacionada;
    }

    public void setEntidadIdRelacionada(Long entidadIdRelacionada) {
        this.entidadIdRelacionada = entidadIdRelacionada;
    }

    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(String detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }
    
}
