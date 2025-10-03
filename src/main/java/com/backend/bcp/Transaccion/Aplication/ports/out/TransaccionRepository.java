package com.backend.bcp.Transaccion.Aplication.ports.out;

import java.util.List;

import com.backend.bcp.Transaccion.Domain.Transaccion;
public interface TransaccionRepository {
    List<Transaccion> buscarUltimosMovimientos(Long cuentaId);
}
