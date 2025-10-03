package com.backend.bcp.app.Cuenta.Aplication.dto;

import java.math.BigDecimal;

public record CuentaDTO(
    Long id,
    String tipo,
    String numeroCuenta,
    BigDecimal saldo) {
} 
