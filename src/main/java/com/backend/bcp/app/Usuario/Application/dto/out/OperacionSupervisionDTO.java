package com.backend.bcp.app.Usuario.Application.dto.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperacionSupervisionDTO(
    Long id,
    String tipoOperacion,
    LocalDateTime fechaHora,
    String descripcion,
    BigDecimal monto,
    String estadoActual
) {
    
}