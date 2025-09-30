package com.backend.bcp.aplicacion.ports.GestionCuentas.out;

import java.util.List;
import com.backend.bcp.dominio.Transaccion;
public interface TransaccionRepository {
    List<Transaccion> buscarUltimosMovimientos(Long cuentaId);
}
