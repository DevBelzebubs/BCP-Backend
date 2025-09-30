package com.backend.bcp.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Pago {
private final Long id;
    private Prestamo prestamo;
    private BigDecimal monto;
    private LocalDate fecha;

    public Pago(Long id, Prestamo prestamo, BigDecimal monto, LocalDate fecha) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id del pago debe ser positivo y no nulo");
        }
        if (prestamo == null) {
            throw new IllegalArgumentException("El pago debe estar asociado a un préstamo válido");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor que cero");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del pago no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del pago no puede estar en el futuro");
        }
        this.id = id;
        this.prestamo = prestamo;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El préstamo no puede ser nulo");
        }
        this.prestamo = prestamo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor que cero");
        }
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del pago no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del pago no puede estar en el futuro");
        }
        this.fecha = fecha;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pago)) return false;
        Pago pago = (Pago) o;
        return Objects.equals(id, pago.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
