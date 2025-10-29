package com.backend.bcp.app.Pago.Application.ports.in;

import java.util.List;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.EditarPagoDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;

public interface RealizarPagoUseCase {
    List<PagoPendienteDTO> listarPagosPendientes(String dni);
    ComprobanteDTO realizarPago(Long cuentaId, Long servicioId);
    PagoPendienteDTO editarPago(Long pagoId, EditarPagoDTO editarPagoDTO);
    void eliminarPago(Long pagoId); 
}
