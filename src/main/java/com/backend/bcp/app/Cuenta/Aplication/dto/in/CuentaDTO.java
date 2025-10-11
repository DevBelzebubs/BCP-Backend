package com.backend.bcp.app.Cuenta.Aplication.dto.in;

import java.math.BigDecimal;


public record CuentaDTO(
    Long id,
    ClienteReferenceDTO cliente,
    String tipo,
    String estadoCuenta,
    String numeroCuenta,
    BigDecimal saldo) {
} 
