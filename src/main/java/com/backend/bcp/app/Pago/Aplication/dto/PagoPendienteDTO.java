package com.backend.bcp.app.Pago.Aplication.dto;

import java.math.BigDecimal;

public record PagoPendienteDTO(Long idPago,
    String nombreServicio,
    BigDecimal montoPendiente) {
}