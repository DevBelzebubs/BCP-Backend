package com.backend.bcp.app.Cuenta.Aplication.dto;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.app.Transaccion.Aplication.dto.MovimientoDTO;

public record DetalleCuentaDTO(Long idCuenta,
    String tipo,
    BigDecimal saldo,
    List<MovimientoDTO> movimientos) {
}
