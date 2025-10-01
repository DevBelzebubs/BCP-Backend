package com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.out;

import java.util.List;

import com.backend.bcp.dominio.Cuenta;
import com.backend.bcp.dominio.Transaccion;

public interface GeneradorEstadoCuentaPdf {
    byte[] generarPdf(Cuenta cuenta, List<Transaccion> movimientos);
}
