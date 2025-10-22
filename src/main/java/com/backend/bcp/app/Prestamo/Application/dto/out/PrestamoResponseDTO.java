package com.backend.bcp.app.Prestamo.Application.dto.out;

import java.math.BigDecimal;

public record PrestamoResponseDTO(Long id,
        Long usuarioId,
        BigDecimal monto,
        int plazoMeses,
        double interes,
        String estado) {
}
