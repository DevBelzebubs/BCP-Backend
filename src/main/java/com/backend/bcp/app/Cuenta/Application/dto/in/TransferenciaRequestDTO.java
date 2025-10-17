package com.backend.bcp.app.Cuenta.Application.dto.in;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(Long idCuentaDestino, BigDecimal monto) {}