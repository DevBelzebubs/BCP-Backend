package com.backend.bcp.app.Reclamo.Infraestructure.entity;

import java.time.LocalDate;

import com.backend.bcp.app.Reclamo.Domain.Reclamo.EstadoReclamo;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reclamo")
public class ReclamoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Reclamo")
    private Long idReclamo;
    @ManyToOne
    @JoinColumn(name = "ID_Cliente", referencedColumnName = "ID_CLIENTE")
    private ClienteEntity cliente;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO")
    private EmpleadoEntity empleado;
    @Column(name = "Fecha_Creacion")
    private LocalDate fechaCreacion;
    @Column(name = "Descripcion")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado_Reclamo")
    private EstadoReclamo estadoReclamo;
    @Column(name = "Respuesta")
    private String respuesta;
    @Column(name = "Numero_Seguimiento", unique = true)
    private String numeroSeguimiento;
    public Long getIdReclamo() {
        return idReclamo;
    }
    public void setIdReclamo(Long idReclamo) {
        this.idReclamo = idReclamo;
    }
    
    public ClienteEntity getCliente() {
        return cliente;
    }
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    public EmpleadoEntity getEmpleado() {
        return empleado;
    }
    public void setEmpleado(EmpleadoEntity empleado) {
        this.empleado = empleado;
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
    public String getNumeroSeguimiento() {
        return numeroSeguimiento;
    }
    public void setNumeroSeguimiento(String numeroSeguimiento) {
        this.numeroSeguimiento = numeroSeguimiento;
    }
    
}
