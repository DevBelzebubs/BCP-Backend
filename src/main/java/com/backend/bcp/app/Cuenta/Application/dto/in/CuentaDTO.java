package com.backend.bcp.app.Cuenta.Application.dto.in;

import java.math.BigDecimal;

public record CuentaDTO(Long id, String tipo, String numeroCuenta, BigDecimal saldo) {

}
