package com.backend.bcp.Pago.Aplication.dto;

import java.math.BigDecimal;

public record PagoPendienteDTO(Long idPago,
    String nombreServicio,
    BigDecimal montoPendiente) {
}