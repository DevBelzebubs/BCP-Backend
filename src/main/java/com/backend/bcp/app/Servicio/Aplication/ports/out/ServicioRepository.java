package com.backend.bcp.app.Servicio.Aplication.ports.out;

import java.util.Optional;

import com.backend.bcp.app.Servicio.Aplication.dto.in.ServicioPersistenceDTO;

public interface ServicioRepository {
    Optional<ServicioPersistenceDTO> obtenerServicioPorId(Long servicioId);
}
