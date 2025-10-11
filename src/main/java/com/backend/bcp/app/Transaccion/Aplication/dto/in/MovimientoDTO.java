package com.backend.bcp.app.Transaccion.Aplication.dto.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoDTO(
    Long id,
    Long cuentaId,
    String tipo,
    BigDecimal monto,
    LocalDateTime fecha) {
}
