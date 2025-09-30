package com.backend.bcp.aplicacion.ports.RealizacionPagos.out;

import java.util.Optional;

import com.backend.bcp.dominio.Servicio;

public interface ServicioRepository {
    Optional<Servicio> obtenerServicioPorId(Long servicioId);
}
