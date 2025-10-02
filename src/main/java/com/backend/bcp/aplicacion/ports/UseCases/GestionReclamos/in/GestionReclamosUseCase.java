package com.backend.bcp.aplicacion.ports.UseCases.GestionReclamos.in;

import java.util.List;

import com.backend.bcp.aplicacion.ports.UseCases.GestionReclamos.dto.ReclamoDTO;

public interface GestionReclamosUseCase {
    void crearReclamo(ReclamoDTO reclamoDTO);
    ReclamoDTO obtenerReclamoPorId(Long id);
    List<ReclamoDTO> obtenerTodosLosReclamos();
}
