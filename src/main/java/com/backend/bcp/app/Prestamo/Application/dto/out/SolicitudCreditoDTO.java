package com.backend.bcp.app.Prestamo.Application.dto.out;

import java.math.BigDecimal;

public record SolicitudCreditoDTO(Long usuarioId,
    BigDecimal monto,
    int plazoMeses) {
}
