package com.backend.bcp.Prestamo.Aplication.ports.in;

import java.util.List;

import com.backend.bcp.Prestamo.Aplication.dto.PrestamoDTO;

public interface SolicitudCreditoUseCase{
    void crearSolicitudCredito();
    List<PrestamoDTO> obtenerSolicitudes();
}
