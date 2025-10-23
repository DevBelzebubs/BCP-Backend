package com.backend.bcp.app.Pago.Application.dto.in;

import java.math.BigDecimal;

public record PagoRequestDTO(Long cuentaId, Long servicioId, BigDecimal monto) {

}
