package com.backend.bcp.app.Pago.Aplication.ports.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.app.Comprobante.Aplication.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Aplication.dto.in.PagoPendienteDTO;

public interface RealizarPagoUseCase {
    List<PagoPendienteDTO> listarPagosPendientes(Long usuarioId);
    ComprobanteDTO realizarPago(Long cuentaId, Long servicioId, BigDecimal monto);
}
