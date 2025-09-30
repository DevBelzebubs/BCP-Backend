package com.backend.bcp.aplicacion.dtos.GestionCuentas;

import java.math.BigDecimal;
import java.util.List;

public record DetalleCuentaDTO(Long idCuenta,
    String tipo,
    BigDecimal saldo,
    List<MovimientoDTO> movimientos) {
}
