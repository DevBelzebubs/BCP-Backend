package com.backend.bcp.Cuenta.Aplication.dto;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.Transaccion.Aplication.dto.MovimientoDTO;

public record DetalleCuentaDTO(Long idCuenta,
    String tipo,
    BigDecimal saldo,
    List<MovimientoDTO> movimientos) {
}
