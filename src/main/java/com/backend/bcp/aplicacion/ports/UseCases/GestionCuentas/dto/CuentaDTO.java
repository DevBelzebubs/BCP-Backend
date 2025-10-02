package com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.dto;

import java.math.BigDecimal;

public record CuentaDTO(
    Long id,
    String tipo,
    String numeroCuenta,
    BigDecimal saldo) {
} 
