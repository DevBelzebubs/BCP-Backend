package com.backend.bcp.app.Cuenta.Application.dto.out;

import java.math.BigDecimal;

import com.backend.bcp.app.Cuenta.Application.dto.in.ClienteReferenceDTO;


public record CuentaPersistenceDTO(
    Long id,
    ClienteReferenceDTO cliente,
    String tipo,
    String estadoCuenta,
    String numeroCuenta,
    BigDecimal saldo) {
} 