package com.backend.bcp.Cuenta.Aplication.dto;

import java.math.BigDecimal;

public record CuentaDTO(
    Long id,
    String tipo,
    String numeroCuenta,
    BigDecimal saldo) {
} 
