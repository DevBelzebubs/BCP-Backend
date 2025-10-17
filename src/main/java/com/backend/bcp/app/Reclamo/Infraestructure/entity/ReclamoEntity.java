package com.backend.bcp.app.Reclamo.Infraestructure.entity;

import java.time.LocalDate;

import com.backend.bcp.app.Reclamo.Domain.Reclamo.EstadoReclamo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reclamo")
public class ReclamoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Reclamo")
    private Long idReclamo;
    @OneToMany
    @JoinColumn(name = "ID_Cliente")
    private Long idCliente;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO")
    private Long idEmpleado;
    @Column(name = "Fecha_Creacion")
    private LocalDate fechaCreacion;
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Estado_Reclamo")
    private EstadoReclamo estadoReclamo;
    @Column(name = "Respuesta")
    private String respuesta;
    public Long getIdReclamo() {
        return idReclamo;
    }
    public void setIdReclamo(Long idReclamo) {
        this.idReclamo = idReclamo;
    }
    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    public Long getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public EstadoReclamo getEstadoReclamo() {
        return estadoReclamo;
    }
    public void setEstadoReclamo(EstadoReclamo estadoReclamo) {
        this.estadoReclamo = estadoReclamo;
    }
    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
}
