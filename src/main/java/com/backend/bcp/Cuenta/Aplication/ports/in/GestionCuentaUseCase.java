package com.backend.bcp.Cuenta.Aplication.ports.in;

import java.util.List;

import com.backend.bcp.Cuenta.Aplication.dto.CuentaDTO;
import com.backend.bcp.Cuenta.Aplication.dto.DetalleCuentaDTO;

public interface GestionCuentaUseCase {
    List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId);
    DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId);
    byte[] generarEstadoCuentaPdf(Long cuentaId);
}
