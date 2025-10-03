package com.backend.bcp.Pago.Aplication.ports.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.Comprobante.Aplication.ports.in.ComprobanteDTO;
import com.backend.bcp.Pago.Aplication.dto.PagoPendienteDTO;

public interface RealizarPagoUseCase {
    List<PagoPendienteDTO> listarPagosPendientes(Long usuarioId);
    ComprobanteDTO realizarPago(Long cuentaId, Long servicioId, BigDecimal monto);
}
