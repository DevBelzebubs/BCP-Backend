package com.backend.bcp.app.Prestamo.Application.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;

public interface CreditoService {
    Optional<PrestamoPersistenceDTO> findById(Long id);
    PrestamoPersistenceDTO guardarSolicitudCredito(PrestamoPersistenceDTO solicitud);
    List<PrestamoPersistenceDTO> findAllSolicitudes();
}
