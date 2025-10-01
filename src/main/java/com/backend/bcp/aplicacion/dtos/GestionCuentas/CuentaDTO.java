package com.backend.bcp.aplicacion.dtos.GestionCuentas;

import java.math.BigDecimal;

public record CuentaDTO(
    Long id,
    String tipo,
    String numeroCuenta,
    BigDecimal saldo) {
} 
