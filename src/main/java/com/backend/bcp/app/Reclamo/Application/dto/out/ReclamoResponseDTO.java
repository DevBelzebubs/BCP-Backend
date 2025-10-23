package com.backend.bcp.app.Reclamo.Application.dto.out;

import java.time.LocalDate;

import com.backend.bcp.app.Reclamo.Domain.Reclamo.EstadoReclamo;

public class ReclamoResponseDTO {
    private Long idReclamo;
    private Long clienteId;
    private Long empleadoId;
    private LocalDate fechaCreacion;
    private String descripcion;
    private EstadoReclamo estadoReclamo;
    private String respuesta;
    private String numeroSeguimiento;
    public ReclamoResponseDTO() {
    }
    
    public ReclamoResponseDTO(Long idReclamo, Long clienteId, Long empleadoId, LocalDate fechaCreacion,
            String descripcion, EstadoReclamo estadoReclamo, String respuesta, String numeroSeguimiento) {
        this.idReclamo = idReclamo;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.fechaCreacion = fechaCreacion;
        this.descripcion = descripcion;
        this.estadoReclamo = estadoReclamo;
        this.respuesta = respuesta;
        this.numeroSeguimiento = numeroSeguimiento;
    }

    public Long getIdReclamo() {
        return idReclamo;
    }
    public void setIdReclamo(Long idReclamo) {
        this.idReclamo = idReclamo;
    }
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public Long getEmpleadoId() {
        return empleadoId;
    }
    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
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
