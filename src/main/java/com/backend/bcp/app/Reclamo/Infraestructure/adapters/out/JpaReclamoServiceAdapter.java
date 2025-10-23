package com.backend.bcp.app.Reclamo.Infraestructure.adapters.out;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Reclamo.Application.dto.in.CrearReclamoRequestDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoPersistenceDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoResponseDTO;
import com.backend.bcp.app.Reclamo.Application.mapper.ReclamoMapper;
import com.backend.bcp.app.Reclamo.Application.ports.in.GestionReclamosUseCase;
import com.backend.bcp.app.Reclamo.Application.ports.out.ReclamoRepository;
import com.backend.bcp.app.Reclamo.Domain.Reclamo;

@Component
public class JpaReclamoServiceAdapter implements GestionReclamosUseCase {
    private final ReclamoRepository repository;
    private final ReclamoMapper mapper
    ;
    public JpaReclamoServiceAdapter(ReclamoRepository repository, ReclamoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    @Transactional
    public ReclamoResponseDTO crearReclamo(CrearReclamoRequestDTO reclamoDTO) {
        Reclamo newReclamo = mapper.requestDtoToDomain(reclamoDTO);

        ReclamoPersistenceDTO dtoParaGuardar = mapper.domainToPersistenceDto(newReclamo);

        String numeroSeguimiento = mapper.generarNumeroSeguimiento();

        ReclamoPersistenceDTO dtoConSeguimiento = new ReclamoPersistenceDTO(
            dtoParaGuardar.idReclamo(), dtoParaGuardar.clienteId(), dtoParaGuardar.empleadoId(),
            dtoParaGuardar.fechaCreacion(), dtoParaGuardar.descripcion(), dtoParaGuardar.estadoReclamo(),
            dtoParaGuardar.respuesta(), numeroSeguimiento
        );

        ReclamoPersistenceDTO dtoGuardado = repository.guardarReclamo(dtoConSeguimiento);

        Reclamo reclamoGuardadoDomain = mapper.persistenceDtoToDomain(dtoGuardado);
        ReclamoResponseDTO responseDTO = mapper.domainToResponseDto(reclamoGuardadoDomain);
        responseDTO.setNumeroSeguimiento(dtoGuardado.numeroSeguimiento());

        return responseDTO;
    }
    @Override
    @Transactional(readOnly = true)
    public ReclamoResponseDTO obtenerReclamoPorId(Long id) {
        return repository.findById(id)
            .map(mapper::persistenceDtoToDomain)
            .map(domain -> {
                ReclamoResponseDTO dto = mapper.domainToResponseDto(domain);
                repository.findById(id).ifPresent(pDto -> dto.setNumeroSeguimiento(pDto.numeroSeguimiento()));
                return dto;
            })
            .orElse(null);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ReclamoResponseDTO> obtenerTodosLosReclamos() {
        List<ReclamoPersistenceDTO> persistenceDTOs = repository.findAll();
        return persistenceDTOs.stream()
            .map(pDto -> {
                Reclamo domain = mapper.persistenceDtoToDomain(pDto);
                ReclamoResponseDTO responseDto = mapper.domainToResponseDto(domain);
                responseDto.setNumeroSeguimiento(pDto.numeroSeguimiento());
                return responseDto;
            })
            .collect(Collectors.toList());
        }
    }

