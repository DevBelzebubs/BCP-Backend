package com.backend.bcp.app.Pago.Application.dto.out;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagoPersistenceDTO(Long id,
    Long prestamoId, 
    Long servicioId, 
    BigDecimal monto,
    LocalDate fecha,
    String estado,
    String tipoPago) {
}
