package com.backend.bcp.app.Cuenta.Aplication.ports.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.app.Comprobante.Aplication.dto.ComprobanteDTO;
import com.backend.bcp.app.Cuenta.Aplication.dto.CuentaDTO;
import com.backend.bcp.app.Cuenta.Aplication.dto.DetalleCuentaDTO;

public interface GestionCuentaUseCase {
    List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId);
    DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId);
    byte[] generarEstadoCuentaPdf(Long cuentaId);
    void iniciarTransferencia(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto);
    ComprobanteDTO confirmarTransferencia(String codigoOTP);
}
