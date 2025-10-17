package com.backend.bcp.app.Cuenta.Application.dto.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoDTO;

public record DetalleCuentaDTO(Long idCuenta,
    String tipo,
    BigDecimal saldo,
    List<MovimientoDTO> movimientos) {
}
