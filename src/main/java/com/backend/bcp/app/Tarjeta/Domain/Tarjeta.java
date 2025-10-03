package com.backend.bcp.app.Tarjeta.Domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.backend.bcp.app.Usuario.Domain.Cliente;

public class Tarjeta {
    private final Long id;
    private Cliente cliente;
    private String numero;
    private LocalDate fechaVencimiento;
    private BigDecimal limiteCredito;

    public Tarjeta(Long id, Cliente cliente, String numero,
                   LocalDate fechaVencimiento, BigDecimal limiteCredito) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de la tarjeta debe ser positivo y no nulo");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("La tarjeta debe estar asociada a un cliente válido");
        }
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de tarjeta no puede ser nulo o vacío");
        }
        if (!numero.matches("\\d{16}")) {
            throw new IllegalArgumentException("El número de tarjeta debe tener exactamente 16 dígitos");
        }
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser nula");
        }
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La tarjeta no puede estar vencida");
        }
        if (limiteCredito == null || limiteCredito.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El límite de crédito debe ser mayor que cero");
        }
        this.id = id;
        this.cliente = cliente;
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
        this.limiteCredito = limiteCredito;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de tarjeta no puede ser nulo o vacío");
        }
        if (!numero.matches("\\d{16}")) {
            throw new IllegalArgumentException("El número de tarjeta debe tener exactamente 16 dígitos");
        }
        this.numero = numero;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser nula");
        }
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La tarjeta no puede estar vencida");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
         if (limiteCredito == null || limiteCredito.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El límite de crédito debe ser mayor que cero");
        }
        this.limiteCredito = limiteCredito;
    }
    public boolean estaActiva() {
        return fechaVencimiento.isAfter(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarjeta)) return false;
        Tarjeta tarjeta = (Tarjeta) o;
        return Objects.equals(id, tarjeta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
