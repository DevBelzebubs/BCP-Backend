package com.backend.bcp.app.Servicio.Application.ports.out;

import java.util.Optional;

import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;

public interface ServicioRepository {
    Optional<ServicioPersistenceDTO> findById(Long servicioId);
    ServicioPersistenceDTO guardarServicio(ServicioPersistenceDTO servicioDTO);
    void eliminarServicio(Long servicioId);
}
