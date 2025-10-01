package com.backend.bcp.aplicacion.ports.UseCases.TransferirFondos.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.aplicacion.dtos.TransferirFondos.ComprobanteDTO;
import com.backend.bcp.aplicacion.dtos.TransferirFondos.CuentaDTO;

public interface TransferirFondosUseCase {
    List<CuentaDTO> listarCuentasDisponibles(Long idCliente);
    boolean validarSaldoCuenta(Long idCuentaOrigen, BigDecimal monto);
    void iniciarTransferencia(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto);
    ComprobanteDTO confirmarTransferencia(String codigoOTP);
}
