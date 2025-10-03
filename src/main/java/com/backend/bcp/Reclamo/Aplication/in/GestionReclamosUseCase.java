package com.backend.bcp.Reclamo.Aplication.in;

import java.util.List;

import com.backend.bcp.Reclamo.Aplication.dto.ReclamoDTO;

public interface GestionReclamosUseCase {
    void crearReclamo(ReclamoDTO reclamoDTO);
    ReclamoDTO obtenerReclamoPorId(Long id);
    List<ReclamoDTO> obtenerTodosLosReclamos();
}
