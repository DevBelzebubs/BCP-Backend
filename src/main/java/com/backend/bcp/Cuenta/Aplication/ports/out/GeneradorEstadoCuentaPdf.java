package com.backend.bcp.Cuenta.Aplication.ports.out;

import java.util.List;

import com.backend.bcp.Cuenta.Domain.Cuenta;
import com.backend.bcp.Transaccion.Domain.Transaccion;

public interface GeneradorEstadoCuentaPdf {
    byte[] generarPdf(Cuenta cuenta, List<Transaccion> movimientos);
}
