package com.backend.bcp.app.Prestamo.Application.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Prestamo.Application.dto.in.SolicitudCreditoDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoResponseDTO;
import com.backend.bcp.app.Prestamo.Application.mapper.PrestamoMapper;
import com.backend.bcp.app.Prestamo.Application.ports.in.SolicitudCreditoUseCase;
import com.backend.bcp.app.Prestamo.Application.ports.out.CreditoService;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Shared.Domain.Usuario;

import jakarta.transaction.Transactional;

@Service
public class SolicitudCreditoServiceImpl implements SolicitudCreditoUseCase {
    private final CreditoService creditoService;
    private final PrestamoMapper mapper;

    public SolicitudCreditoServiceImpl(CreditoService creditoService, PrestamoMapper mapper) {
        this.creditoService = creditoService;
        this.mapper = mapper;
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

    @Override
    public void aprobarSolicitud(Long solicitudId, Long asesorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aprobarSolicitud'");
    }

    @Override
    public void rechazarSolicitud(Long solicitudId, Long asesorId, String motivo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rechazarSolicitud'");
    }

    @Override
    public void marcarPendiente(Long solicitudId, Long asesorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'marcarPendiente'");
    }

}
