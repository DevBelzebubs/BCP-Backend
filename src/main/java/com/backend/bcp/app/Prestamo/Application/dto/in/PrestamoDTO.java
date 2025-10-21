package com.backend.bcp.app.Prestamo.Application.dto.in;

import java.math.BigDecimal;

public record PrestamoDTO(Long id,
        Long usuarioId,
        BigDecimal monto,
        int plazo,
        double interes,
        String estado) {
}
