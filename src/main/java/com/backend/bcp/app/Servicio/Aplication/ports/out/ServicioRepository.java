package com.backend.bcp.app.Servicio.Aplication.ports.out;

import java.util.Optional;

import com.backend.bcp.app.Servicio.Domain.Servicio;

public interface ServicioRepository {
    Optional<Servicio> obtenerServicioPorId(Long servicioId);
}
