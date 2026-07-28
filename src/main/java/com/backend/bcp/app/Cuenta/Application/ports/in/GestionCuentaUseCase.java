package com.backend.bcp.app.Cuenta.Application.ports.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.AbrirCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;

public interface GestionCuentaUseCase {
    CuentaDTO crearCuenta(CuentaDTO cuentaDTO, String dni);
    CuentaDTO abrirCuenta(AbrirCuentaDTO dto);
    List<CuentaDTO> listarCuentasPorUsuario(String dni);
    DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId);
    byte[] generarEstadoCuentaPdf(Long cuentaId);
    void iniciarTransferencia(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto);
    ComprobanteDTO confirmarTransferencia(String dni, String codigoOTP);
}
