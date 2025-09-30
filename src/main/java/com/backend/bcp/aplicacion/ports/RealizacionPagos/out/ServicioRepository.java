package com.backend.bcp.aplicacion.ports.RealizacionPagos.out;

import java.util.Optional;

public interface ServicioRepository {
    Optional<Servicio> obtenerServicioPorId(Long servicioId);
}
