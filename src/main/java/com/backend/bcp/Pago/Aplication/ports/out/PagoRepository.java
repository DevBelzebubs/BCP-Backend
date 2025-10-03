package com.backend.bcp.Pago.Aplication.ports.out;

import java.util.List;

import com.backend.bcp.Pago.Domain.Pago;

public interface PagoRepository {
    List<Pago> obtenerPendientesPorUsuario(Long usuarioId);
    void registrarPago(Pago pago);
}
