package com.backend.bcp.app.Reclamo.Application.ports.in;

import java.util.List;

import com.backend.bcp.app.Reclamo.Application.dto.ReclamoDTO;

public interface GestionReclamosUseCase {
    void crearReclamo(ReclamoDTO reclamoDTO);
    ReclamoDTO obtenerReclamoPorId(Long id);
    List<ReclamoDTO> obtenerTodosLosReclamos();
}
