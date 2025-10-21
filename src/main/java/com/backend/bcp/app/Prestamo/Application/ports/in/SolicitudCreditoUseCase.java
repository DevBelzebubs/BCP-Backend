package com.backend.bcp.app.Prestamo.Application.ports.in;

import java.util.List;

import com.backend.bcp.app.Prestamo.Application.dto.in.PrestamoDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.SolicitudCreditoDTO;

public interface SolicitudCreditoUseCase{
    PrestamoDTO crearSolicitudCredito(SolicitudCreditoDTO solicitudDTO);
    List<PrestamoDTO> obtenerSolicitudes();
    PrestamoDTO findSolicitudById(Long id);
    void aprobarSolicitud(Long solicitudId, Long asesorId);
    void rechazarSolicitud(Long solicitudId, Long asesorId, String motivo);
    void marcarPendiente(Long solicitudId, Long asesorId);
}
