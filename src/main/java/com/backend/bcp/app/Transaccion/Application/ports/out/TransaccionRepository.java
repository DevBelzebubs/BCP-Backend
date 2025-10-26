package com.backend.bcp.app.Transaccion.Application.ports.out;

import java.time.LocalDate;
import java.util.List;

import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
public interface TransaccionRepository {
    List<MovimientoAppDTO> buscarUltimosMovimientos(Long cuentaId);
    void guardarTransaccion(MovimientoAppDTO transaccion);
    List<MovimientoAppDTO> buscarMovimientosPorFecha(LocalDate fecha);
}