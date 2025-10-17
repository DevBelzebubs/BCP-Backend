package com.backend.bcp.app.Usuario.Application.ports.in.Cliente;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;

public interface TransferirFondosUseCase {
    List<Cuenta> listarCuentasDisponibles(Long idCliente);
    boolean validarSaldoCuenta(Long idCuentaOrigen, BigDecimal monto);
    void iniciarTransferencia(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto);
    ComprobanteDTO confirmarTransferencia(String codigoOTP);
}