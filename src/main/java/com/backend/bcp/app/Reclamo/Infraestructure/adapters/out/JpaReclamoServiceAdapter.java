package com.backend.bcp.app.Reclamo.Infraestructure.adapters.out;

import java.time.LocalDate;
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
import com.backend.bcp.app.Reclamo.Domain.Reclamo.EstadoReclamo;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;

@Component
public class JpaReclamoServiceAdapter implements GestionReclamosUseCase {
    private final ReclamoRepository repository;
    private final ReclamoMapper mapper;
    private final SpringDataClientRepository clientRepository;
    private final UserRepository userRepository;

    public JpaReclamoServiceAdapter(ReclamoRepository repository, ReclamoMapper mapper, SpringDataClientRepository clientRepository, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public ReclamoResponseDTO crearReclamo(CrearReclamoRequestDTO reclamoDTO) {
         Long usuarioId = userRepository.findByDni(reclamoDTO.getDniCliente()) 
                 .map(UsuarioDTO::id)
                 .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + reclamoDTO.getDniCliente()));

         ClienteEntity clienteEntity = clientRepository.findByIdUsuario_Id(usuarioId)
                 .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el ID de Usuario: " + usuarioId));
         String numeroSeguimiento = mapper.generarNumeroSeguimiento();
         ReclamoPersistenceDTO dtoParaGuardar = new ReclamoPersistenceDTO(
                 null,
                 clienteEntity.getIdCliente(),
                 null,
                 LocalDate.now(),
                 reclamoDTO.getDescripcion(),
                 EstadoReclamo.PENDIENTE,
                 null,
                 numeroSeguimiento);
         ReclamoPersistenceDTO dtoGuardado = repository.guardarReclamo(dtoParaGuardar);

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

    @Override
    @Transactional(readOnly = true)
    public List<ReclamoResponseDTO> obtenerReclamosPorClienteId(Long clienteId) {
        List<ReclamoPersistenceDTO> persistenceDTOs = repository.findAll();
        return persistenceDTOs.stream()
            .filter(pDto -> pDto.clienteId() != null && pDto.clienteId().equals(clienteId))
            .map(pDto -> {
                Reclamo domain = mapper.persistenceDtoToDomain(pDto);
                ReclamoResponseDTO responseDto = mapper.domainToResponseDto(domain);
                responseDto.setNumeroSeguimiento(pDto.numeroSeguimiento());
                return responseDto;
            })
            .collect(Collectors.toList());
    }
    }

