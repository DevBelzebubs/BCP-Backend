package com.backend.bcp.aplicacion.ports.Servicio.Aplication.ports.out;

import java.util.Optional;

import com.backend.bcp.aplicacion.ports.Servicio.Domain.Servicio;

public interface ServicioRepository {
    Optional<Servicio> obtenerServicioPorId(Long servicioId);
}
