package com.backend.bcp.app.Prestamo.Application.dto.out;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrestamoPersistenceDTO(
    Long id,
    Long usuarioId,
    BigDecimal monto,
    double interes,
    int plazoMeses,
    LocalDate fechaInicio,
    String estado
) {

}
