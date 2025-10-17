package com.backend.bcp.app.Prestamo.Application.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Prestamo.Domain.Prestamo;

public interface CreditoService {
    Optional<Prestamo> findById(Long id);
    void guardarSolicitudCredito(Prestamo solicitud);
    List<Prestamo> findAllSolicitudes();
    List<Prestamo> findSolicitudesById(Long id);  
}
