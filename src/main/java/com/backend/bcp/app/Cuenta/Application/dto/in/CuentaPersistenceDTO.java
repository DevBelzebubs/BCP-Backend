package com.backend.bcp.app.Cuenta.Application.dto.in;

import java.math.BigDecimal;


public record CuentaPersistenceDTO(
    Long id,
    ClienteReferenceDTO cliente,
    String tipo,
    String estadoCuenta,
    String numeroCuenta,
    BigDecimal saldo) {
} 