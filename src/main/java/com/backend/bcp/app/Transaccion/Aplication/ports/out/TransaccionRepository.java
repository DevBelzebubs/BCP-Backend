package com.backend.bcp.app.Transaccion.Aplication.ports.out;

import java.util.List;

import com.backend.bcp.app.Transaccion.Domain.Transaccion;
public interface TransaccionRepository {
    List<Transaccion> buscarUltimosMovimientos(Long cuentaId);
    void guardarTransaccion(Transaccion transaccion);
}
