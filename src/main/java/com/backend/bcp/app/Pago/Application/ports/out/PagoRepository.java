package com.backend.bcp.app.Pago.Application.ports.out;

import java.util.List;

import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;

public interface PagoRepository {
    List<PagoPersistenceDTO> obtenerPendientesPorUsuario(Long usuarioId);
    void registrarPago(Pago pago);
}
