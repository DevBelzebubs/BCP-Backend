package com.backend.bcp.dominio;

import java.time.LocalDate;

public class Reclamo {
    private final Long id;
    private Usuario cliente;
    private Usuario empleado;
    private final LocalDate fechaCreacion;
    private String descripcion;
    private EstadoReclamo estado;
    private String respuesta;
    public enum EstadoReclamo {
        PENDIENTE,
        EN_REVISION,
        ATENDIDO
    }
    public Reclamo(Long id, Usuario cliente, Usuario empleado, LocalDate fechaCreacion, String descripcion,
            EstadoReclamo estado, String respuesta) {
                if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del reclamo debe ser positivo");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El reclamo debe estar asociado a un cliente");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del reclamo no puede estar vacía");
        }
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula");
        }
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.fechaCreacion = fechaCreacion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.respuesta = respuesta;
    }
    public Long getId() {
        return id;
    }
    public Usuario getCliente() {
        return cliente;
    }
    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }
    public Usuario getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public EstadoReclamo getEstado() {
        return estado;
    }
    public void setEstado(EstadoReclamo estado) {
        this.estado = estado;
    }
    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    public void asignarEmpleado(Usuario empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("Debe asignarse un empleado válido");
        }
        this.empleado = empleado;
        this.estado = EstadoReclamo.EN_REVISION;
    }
    public void atender(String respuesta) {
        if (this.empleado == null) {
            throw new IllegalStateException("No se puede atender un reclamo sin empleado asignado");
        }
        this.estado = EstadoReclamo.ATENDIDO;
        this.respuesta = respuesta;
    }
}
