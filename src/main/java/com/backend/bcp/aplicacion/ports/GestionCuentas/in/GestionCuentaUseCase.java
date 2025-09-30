package com.backend.bcp.aplicacion.ports.GestionCuentas.in;

import java.util.List;

import com.backend.bcp.aplicacion.dtos.GestionCuentas.CuentaDTO;
import com.backend.bcp.aplicacion.dtos.GestionCuentas.DetalleCuentaDTO;

public interface GestionCuentaUseCase {
    List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId);
    DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId);
    byte[] generarEstadoCuentaPdf(Long cuentaId);
}
