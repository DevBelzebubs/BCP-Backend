package com.backend.bcp.app.Comprobante.Application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ComprobanteDTO(Long idPago,
    String servicio,
    BigDecimal montoPagado,
    LocalDate fecha,
    String codigoAutorizacion
) {
}
