package com.backend.bcp.dominio;

import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta {
    private final Long id;
    private Usuario usuario;
    private String tipo;
    private BigDecimal saldo;
    public Cuenta(Long id, Usuario usuario, String tipo, BigDecimal saldo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id de la cuenta debe ser positivo y no nulo");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("La cuenta debe estar asociada a un usuario válido");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de cuenta no puede ser vacío");
        }
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser nulo ni negativo");
        }
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.saldo = saldo;
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
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de cuenta no puede ser vacío");
        }
        this.tipo = tipo;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo no puede ser nulo ni negativo");
        }
        this.saldo = saldo;
    }
    public void depositar(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor que cero");
        }
        this.saldo = this.saldo.add(monto);
    }

    public void retirar(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor que cero");
        }
        if (this.saldo.compareTo(monto) < 0) {
            throw new IllegalStateException("Fondos insuficientes para realizar el retiro");
        }
        this.saldo = this.saldo.subtract(monto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta)) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
