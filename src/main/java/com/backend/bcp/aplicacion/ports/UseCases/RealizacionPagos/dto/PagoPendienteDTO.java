package com.backend.bcp.aplicacion.ports.UseCases.RealizacionPagos.dto;

import java.math.BigDecimal;

public record PagoPendienteDTO(Long idPago,
    String nombreServicio,
    BigDecimal montoPendiente) {
}