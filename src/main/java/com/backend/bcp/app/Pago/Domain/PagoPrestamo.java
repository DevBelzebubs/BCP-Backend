package com.backend.bcp.app.Pago.Domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.bcp.app.Prestamo.Domain.Prestamo;

public class PagoPrestamo extends Pago {
    private final Prestamo prestamo;
    public PagoPrestamo(Long id, BigDecimal monto, LocalDate fecha, Prestamo prestamo, String estado, Long clienteId) {
        super(id, monto, fecha,estado,clienteId);
        if (prestamo == null) throw new IllegalArgumentException("El pago debe estar asociado a un préstamo.");
        this.prestamo = prestamo;
    }
    public Prestamo getPrestamo() {
        return prestamo;
    }
    
}
