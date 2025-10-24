package com.backend.bcp.app.Shared.Infraestructure.entity;

import java.time.LocalDateTime;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.EstadoAlerta;
import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoAlerta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Alerta")
public class AlertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Alerta")
    private Long idAlerta;

    @Column(name = "FechaHora_Creacion", nullable = false)
    private LocalDateTime fechaHoraCreacion;

    @Column(name = "Tipo_Alerta", nullable = false)
    private TipoAlerta tipoAlerta;

    @Column(name = "Mensaje", nullable = false, length = 500)
    private String mensaje;

    @Column(name = "Estado", nullable = false)
    private EstadoAlerta estado;

    @Column(name = "Usuario_Notificado_ID")
    private Long usuarioNotificadoId;

    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public EstadoAlerta getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlerta estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getUsuarioNotificadoId() {
        return usuarioNotificadoId;
    }

    public void setUsuarioNotificadoId(Long usuarioNotificadoId) {
        this.usuarioNotificadoId = usuarioNotificadoId;
    }
    
}
