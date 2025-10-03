package com.backend.bcp.app.Pago.Domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Servicio.Domain.Servicio;

public class Pago {
    private final Long id;
    private Prestamo prestamo;
    private Servicio servicio;
    private BigDecimal monto;
    private LocalDate fecha;

    private Pago(Long id, BigDecimal monto, LocalDate fecha, Prestamo prestamo, Servicio servicio) {
        if (id == null || id <= 0) throw new IllegalArgumentException("El id debe ser válido");
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El monto debe ser mayor que cero");
        if (fecha == null || fecha.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha no puede ser futura");
        if (prestamo == null && servicio == null) throw new IllegalArgumentException("Debe asociarse a un préstamo o a un servicio");
        if (prestamo != null && servicio != null) throw new IllegalArgumentException("No puede asociarse a ambos");

        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.prestamo = prestamo;
        this.servicio = servicio;
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
    public static Pago pagoDePrestamo(Long id, BigDecimal monto, LocalDate fecha, Prestamo prestamo) {
        return new Pago(id, monto, fecha, prestamo, null);
    }

    public static Pago pagoDeServicio(Long id, BigDecimal monto, LocalDate fecha, Servicio servicio) {
        return new Pago(id, monto, fecha, null, servicio);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser nulo");
        }
        this.servicio = servicio;
    }
}
