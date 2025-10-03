package com.backend.bcp.Prestamo.Aplication.ports.out;

import com.backend.bcp.Prestamo.Domain.Prestamo;

public interface CreditoService {
    void guardarSolicitudCredito(Prestamo solicitud);
}
