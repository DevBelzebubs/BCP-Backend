package com.backend.bcp.aplicacion.ports.UseCases.RealizacionPagos.in;

import java.math.BigDecimal;
import java.util.List;

import com.backend.bcp.aplicacion.dtos.RealizacionPagos.ComprobanteDTO;
import com.backend.bcp.aplicacion.dtos.RealizacionPagos.PagoPendienteDTO;

public interface RealizarPagoUseCase {
    List<PagoPendienteDTO> listarPagosPendientes(Long usuarioId);
    ComprobanteDTO realizarPago(Long cuentaId, Long servicioId, BigDecimal monto);
}
