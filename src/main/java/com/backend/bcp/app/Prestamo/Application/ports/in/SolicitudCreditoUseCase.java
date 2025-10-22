package com.backend.bcp.app.Prestamo.Application.ports.in;

import java.util.List;

import com.backend.bcp.app.Prestamo.Application.dto.in.SolicitudCreditoDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoResponseDTO;

public interface SolicitudCreditoUseCase{
    PrestamoResponseDTO crearSolicitudCredito(SolicitudCreditoDTO solicitudDTO);
    List<PrestamoResponseDTO> obtenerSolicitudes();
    PrestamoResponseDTO findSolicitudById(Long id);

    void aprobarSolicitud(Long solicitudId, Long asesorId);
    void rechazarSolicitud(Long solicitudId, Long asesorId, String motivo);
    void marcarPendiente(Long solicitudId, Long asesorId);
}
