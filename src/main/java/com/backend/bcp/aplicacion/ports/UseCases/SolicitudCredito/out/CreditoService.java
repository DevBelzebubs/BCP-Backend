package com.backend.bcp.aplicacion.ports.UseCases.SolicitudCredito.out;

import com.backend.bcp.dominio.Prestamo;

public interface CreditoService {
    void guardarSolicitudCredito(Prestamo solicitud);
}
