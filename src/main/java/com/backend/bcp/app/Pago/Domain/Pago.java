package com.backend.bcp.app.Pago.Domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Pago {
    protected final Long id;
    protected BigDecimal monto;
    protected LocalDate fecha;
    protected String estado;

    public Pago(Long id, BigDecimal monto, LocalDate fecha, String estado) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
    }
    
    public Long getId() {
        return id;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
