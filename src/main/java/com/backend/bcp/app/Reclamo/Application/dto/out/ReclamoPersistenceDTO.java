package com.backend.bcp.app.Reclamo.Application.dto.out;

import java.time.LocalDate;

import com.backend.bcp.app.Reclamo.Domain.Reclamo.EstadoReclamo;

public record ReclamoPersistenceDTO(Long idReclamo,
    Long clienteId,
    Long empleadoId,
    LocalDate fechaCreacion,
    String descripcion,
    EstadoReclamo estadoReclamo,
    String respuesta,
    String numeroSeguimiento) {
    public ReclamoPersistenceDTO(Long clienteId, LocalDate fechaCreacion, String descripcion, EstadoReclamo estado) {
        this(null, clienteId, null, fechaCreacion, descripcion, estado, null, null);
    }
}
