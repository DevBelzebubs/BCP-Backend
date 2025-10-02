package com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.in;

import java.util.List;

import com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.dto.CuentaDTO;
import com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.dto.DetalleCuentaDTO;

public interface GestionCuentaUseCase {
    List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId);
    DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId);
    byte[] generarEstadoCuentaPdf(Long cuentaId);
}
