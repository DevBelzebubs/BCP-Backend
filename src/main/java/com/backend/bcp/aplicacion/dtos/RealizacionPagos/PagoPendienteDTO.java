package com.backend.bcp.aplicacion.dtos.RealizacionPagos;

import java.math.BigDecimal;

public record PagoPendienteDTO(Long idPago,
    String nombreServicio,
    BigDecimal montoPendiente) {
}