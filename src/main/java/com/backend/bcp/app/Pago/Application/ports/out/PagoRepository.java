package com.backend.bcp.app.Pago.Application.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Pago.Application.dto.in.EditarPagoDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;

public interface PagoRepository {
    List<PagoPersistenceDTO> obtenerPendientesPorUsuario(Long usuarioId);
    void registrarPago(Pago pago);
    Optional<PagoPersistenceDTO> findById(Long pagoId);
    
    PagoPendienteDTO editarPago(Long pagoId, EditarPagoDTO editarPagoDTO);
    void eliminarPago(Long pagoId);
}
