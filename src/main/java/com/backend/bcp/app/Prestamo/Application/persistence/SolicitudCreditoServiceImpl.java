package com.backend.bcp.app.Prestamo.Application.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Prestamo.Application.dto.in.SolicitudCreditoDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoResponseDTO;
import com.backend.bcp.app.Prestamo.Application.mapper.PrestamoMapper;
import com.backend.bcp.app.Prestamo.Application.ports.in.SolicitudCreditoUseCase;
import com.backend.bcp.app.Prestamo.Application.ports.out.CreditoService;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Usuario.Application.ports.out.Cliente.NotificacionService;
import com.backend.bcp.app.shared.Domain.Usuario;

import jakarta.transaction.Transactional;

@Service
public class SolicitudCreditoServiceImpl implements SolicitudCreditoUseCase {
    private final CreditoService creditoService;
    private final PrestamoMapper mapper;
    private final NotificacionService notificacionService;

    public SolicitudCreditoServiceImpl(CreditoService creditoService, PrestamoMapper mapper,NotificacionService notificacionService) {
        this.creditoService = creditoService;
        this.mapper = mapper;
        this.notificacionService = notificacionService;
    }

    @Override
    @Transactional
    public PrestamoResponseDTO crearSolicitudCredito(SolicitudCreditoDTO solicitudDTO) {
        if (solicitudDTO.getMonto() == null || solicitudDTO.getPlazoMeses() <= 0) {
            throw new IllegalArgumentException("Datos incompletos para la solicitud.");
        }
        Usuario usuario = new Usuario();
        usuario.setId(solicitudDTO.getUsuarioId());
        double interes = 15.0;
        Prestamo solicitudPrestamo = new Prestamo(null,
                usuario,
                solicitudDTO.getMonto(),
                interes,
                solicitudDTO.getPlazoMeses(),
                LocalDate.now());
        PrestamoPersistenceDTO dtoParaGuardar = mapper.toPersistenceDTO(solicitudPrestamo);

        PrestamoPersistenceDTO dtoGuardado = creditoService.guardarSolicitudCredito(dtoParaGuardar);

        Prestamo solicitudGuardada = mapper.fromPersistenceDTO(dtoGuardado);

        return mapper.toDto(solicitudGuardada);
    }

    @Override
    public List<PrestamoResponseDTO> obtenerSolicitudes() {
        return creditoService.findAllSolicitudes().stream()
            .map(mapper::fromPersistenceDTO)
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public PrestamoResponseDTO findSolicitudById(Long id) {
        return creditoService.findById(id)
            .map(mapper::fromPersistenceDTO)
            .map(mapper::toDto)
            .orElse(null);
    }
    @Transactional
    private void actualizarEstadoPrestamo(Long solicitudId, String nuevoEstado, String motivoNotificacion) {
        PrestamoPersistenceDTO dto = creditoService.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud de préstamo no encontrada con ID: " + solicitudId));
        Prestamo prestamo = mapper.fromPersistenceDTO(dto);
        prestamo.setEstado(nuevoEstado);
        PrestamoPersistenceDTO dtoActualizado = mapper.toPersistenceDTO(prestamo);
        creditoService.guardarSolicitudCredito(dtoActualizado);
        
        notificacionService.notificarCliente(prestamo.getUsuario().getId(), motivoNotificacion);
    }
    @Override
    @Transactional
    public void aprobarSolicitud(Long solicitudId, Long asesorId) {
        actualizarEstadoPrestamo(solicitudId, "APROBADO", 
            "¡Felicidades! Su solicitud de préstamo ha sido APROBADA.");
    }

    @Override
    @Transactional
    public void rechazarSolicitud(Long solicitudId, Long asesorId, String motivo) {
        String motivoNotificacion = "Su solicitud de préstamo ha sido RECHAZADA. Motivo: " 
            + (motivo != null && !motivo.isBlank() ? motivo : "Evaluación crediticia no favorable.");

            actualizarEstadoPrestamo(solicitudId, "RECHAZADO", motivoNotificacion);
    }

    @Override
    @Transactional
    public void marcarPendiente(Long solicitudId, Long asesorId) {
        actualizarEstadoPrestamo(solicitudId, "PENDIENTE_DOCUMENTACION", 
            "Su solicitud de préstamo requiere documentación adicional. Un asesor se contactará con usted.");
    }

}
