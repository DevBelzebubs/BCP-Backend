package com.backend.bcp.app.Pago.Aplication.ports.out;

import java.util.List;

import com.backend.bcp.app.Pago.Aplication.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;

public interface PagoRepository {
    List<PagoPersistenceDTO> obtenerPendientesPorUsuario(Long usuarioId);
    void registrarPago(Pago pago);
}
