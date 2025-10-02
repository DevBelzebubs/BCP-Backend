package com.backend.bcp.aplicacion.ports.UseCases.SolicitudCredito.in;

import java.util.List;

import com.backend.bcp.aplicacion.ports.UseCases.SolicitudCredito.dto.PrestamoDTO;

public interface SolicitudCreditoUseCase{
    void crearSolicitudCredito();
    List<PrestamoDTO> obtenerSolicitudes();
}
