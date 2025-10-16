package com.backend.bcp.app.Pago.Domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.bcp.app.Servicio.Domain.Servicio;

public class PagoServicio extends Pago {
    private final Servicio servicio;
    public PagoServicio(Long id, BigDecimal monto, LocalDate fecha, Servicio servicio,String estado, Long clienteId) {
        super(id, monto, fecha,estado,clienteId);
        if (servicio == null) throw new IllegalArgumentException("El pago debe estar asociado a un servicio.");
        this.servicio = servicio;
    }
    public Servicio getServicio() {
        return servicio;
    }
    
}
