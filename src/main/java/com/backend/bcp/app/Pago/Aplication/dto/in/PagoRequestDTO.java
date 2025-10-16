package com.backend.bcp.app.Pago.Aplication.dto.in;

import java.math.BigDecimal;

public record PagoRequestDTO(Long cuentaId, Long itemPagoId, BigDecimal monto) {

}
