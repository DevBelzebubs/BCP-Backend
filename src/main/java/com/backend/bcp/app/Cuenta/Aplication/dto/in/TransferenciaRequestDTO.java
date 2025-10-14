package com.backend.bcp.app.Cuenta.Aplication.dto.in;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(Long idCuentaDestino, BigDecimal monto) {}