package com.backend.bcp.app.Servicio.Application.dto.in;

import java.math.BigDecimal;

public class EditarServicioDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal recibo;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getRecibo() {
        return recibo;
    }
    public void setRecibo(BigDecimal recibo) {
        this.recibo = recibo;
    }
}
