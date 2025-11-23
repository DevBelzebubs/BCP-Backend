package com.backend.bcp.app.Reclamo.Application.ports.in;

import java.util.List;

import com.backend.bcp.app.Reclamo.Application.dto.in.CrearReclamoRequestDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoResponseDTO;

public interface GestionReclamosUseCase {
    ReclamoResponseDTO crearReclamo(CrearReclamoRequestDTO reclamoDTO);
    ReclamoResponseDTO obtenerReclamoPorId(Long id);
    List<ReclamoResponseDTO> obtenerTodosLosReclamos();
    List<ReclamoResponseDTO> obtenerReclamosPorClienteId(Long clienteId);
}
