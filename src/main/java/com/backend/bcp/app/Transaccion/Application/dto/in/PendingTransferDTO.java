package com.backend.bcp.app.Transaccion.Application.dto.in;

import java.math.BigDecimal;

public record PendingTransferDTO(Long idCliente,
    Long idCuentaOrigen, 
    Long idCuentaDestino, 
    BigDecimal monto) {
}
