package com.backend.bcp.app.Transaccion.Domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.backend.bcp.app.Cuenta.Domain.Cuenta;

public class Transaccion {
    private final Long id;
    private Cuenta cuenta;
    private String tipo;
    private BigDecimal monto;
    private LocalDateTime fecha;

    public Transaccion(Long id, Cuenta cuenta, String tipo,
                       BigDecimal monto, LocalDateTime fecha) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La transacción debe estar asociada a una cuenta válida");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo o vacío");
        }
        if (!tipo.equalsIgnoreCase("DEPOSITO") && !tipo.equalsIgnoreCase("RETIRO")) {
            throw new IllegalArgumentException("El tipo de transacción debe ser DEPOSITO o RETIRO");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la transacción no puede estar en el futuro");
        }
        this.id = id;
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
        this.cuenta = cuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo o vacío");
        }
        if (!tipo.equalsIgnoreCase("DEPOSITO") && !tipo.equalsIgnoreCase("RETIRO")) {
            throw new IllegalArgumentException("El tipo de transacción debe ser DEPOSITO o RETIRO");
        }
        this.tipo = tipo;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la transacción no puede estar en el futuro");
        }
        this.fecha = fecha;
    }
    public void aplicar() {
        if (tipo.equals("DEPOSITO")) {
            cuenta.depositar(monto);
        } else if (tipo.equals("RETIRO")) {
            cuenta.retirar(monto);
        } else {
            throw new IllegalStateException("Tipo de transacción inválido");
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaccion)) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
