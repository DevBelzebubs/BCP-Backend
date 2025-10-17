package com.backend.bcp.app.Pago.Application.dto.in;

import java.math.BigDecimal;

public record PagoPendienteDTO(Long idPago,
    String nombreServicio,
    BigDecimal montoPendiente) {
}