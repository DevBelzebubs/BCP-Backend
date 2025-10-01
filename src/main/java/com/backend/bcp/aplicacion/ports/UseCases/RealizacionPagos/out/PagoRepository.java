package com.backend.bcp.aplicacion.ports.UseCases.RealizacionPagos.out;

import java.util.List;

import com.backend.bcp.dominio.Pago;

public interface PagoRepository {
    List<Pago> obtenerPendientesPorUsuario(Long usuarioId);
    void registrarPago(Pago pago);
}
