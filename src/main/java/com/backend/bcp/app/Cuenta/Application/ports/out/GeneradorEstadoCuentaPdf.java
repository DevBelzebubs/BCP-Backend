package com.backend.bcp.app.Cuenta.Application.ports.out;

import java.util.List;

import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;

public interface GeneradorEstadoCuentaPdf {
    byte[] generarPdf(Cuenta cuenta, List<Transaccion> movimientos);
}
