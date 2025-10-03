package com.backend.bcp.Transaccion.Aplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoDTO(
    Long id,
    String tipo,
    BigDecimal monto,
    LocalDateTime fecha) {
}
