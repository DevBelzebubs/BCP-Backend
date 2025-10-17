package com.backend.bcp.app.Transaccion.Application.ports.out;

import java.util.List;

import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
public interface TransaccionRepository {
    List<MovimientoPersistenceDTO> buscarUltimosMovimientos(Long cuentaId);
    void guardarTransaccion(Transaccion transaccion);
}
