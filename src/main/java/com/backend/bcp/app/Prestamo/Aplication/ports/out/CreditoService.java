package com.backend.bcp.app.Prestamo.Aplication.ports.out;

import com.backend.bcp.app.Prestamo.Domain.Prestamo;

public interface CreditoService {
    void guardarSolicitudCredito(Prestamo solicitud);
}
