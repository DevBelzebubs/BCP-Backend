package com.backend.bcp.app.Prestamo.Domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.backend.bcp.app.shared.Domain.Usuario;

import java.math.RoundingMode;

public class Prestamo {
    private final Long id;
    private Usuario usuario;
    private BigDecimal monto;
    private double interes;
    private int plazoMeses;
    private LocalDate fechaInicio;

    public Prestamo(Long id, Usuario usuario, BigDecimal monto,
                    double interes, int plazoMeses, LocalDate fechaInicio) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id del préstamo debe ser positivo y no nulo");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("El préstamo debe estar asociado a un usuario válido");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor que cero");
        }
        if (interes < 0 || interes > 100) {
            throw new IllegalArgumentException("El interés debe estar entre 0% y 100%");
        }
        if (plazoMeses <= 0) {
            throw new IllegalArgumentException("El plazo en meses debe ser mayor que cero");
        }
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar en el futuro");
        }
        this.id = id;
        this.usuario = usuario;
        this.monto = monto;
        this.interes = interes;
        this.plazoMeses = plazoMeses;
        this.fechaInicio = fechaInicio;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        this.usuario = usuario;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        this.monto = monto;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        if (interes < 0 || interes > 100) {
            throw new IllegalArgumentException("El interés debe estar entre 0% y 100%");
        }
        this.interes = interes;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
         if (plazoMeses <= 0) {
            throw new IllegalArgumentException("El plazo debe ser mayor que cero");
        }
        this.plazoMeses = plazoMeses;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar en el futuro");
        }
        this.fechaInicio = fechaInicio;
    }
    public BigDecimal calcularCuotaMensual() {
        if (plazoMeses <= 0) {
            throw new IllegalStateException("El plazo debe ser mayor que cero para calcular la cuota");
        }

        BigDecimal tasaMensual = BigDecimal.valueOf(interes / 100.0 / 12.0);

        if (tasaMensual.compareTo(BigDecimal.ZERO) == 0) {
            return monto.divide(BigDecimal.valueOf(plazoMeses), 2, RoundingMode.HALF_UP);
        }
        double i = tasaMensual.doubleValue();
        double n = plazoMeses;
        double p = monto.doubleValue();
        double cuota = p * (i / (1 - Math.pow(1 + i, -n)));
        return BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo)) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(id, prestamo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
