package com.backend.bcp.app.Servicio.Application.ports.in;

import com.backend.bcp.app.Servicio.Application.dto.in.CrearServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.in.EditarServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.out.ServicioResponseDTO;

public interface GestionServicioUseCase {
    ServicioResponseDTO crearServicio(CrearServicioDTO crearDto);
    ServicioResponseDTO editarServicio(Long id, EditarServicioDTO editarDto);
    void eliminarServicio(Long id);
    ServicioResponseDTO obtenerServicio(Long id);
}
