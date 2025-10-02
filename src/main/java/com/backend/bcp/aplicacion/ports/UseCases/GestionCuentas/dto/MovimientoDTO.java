package com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoDTO(
    Long id,
    String tipo,
    BigDecimal monto,
    LocalDateTime fecha) {
}
