package com.backend.bcp.app.Servicio.Application.persistence;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Servicio.Application.dto.in.CrearServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.in.EditarServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Application.dto.out.ServicioResponseDTO;
import com.backend.bcp.app.Servicio.Application.mapper.ServicioMapper;
import com.backend.bcp.app.Servicio.Application.ports.in.GestionServicioUseCase;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
import com.backend.bcp.app.Servicio.Domain.Servicio;

@Service
public class GestionServicioImpl implements GestionServicioUseCase {
    private final ServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;

    public GestionServicioImpl(ServicioRepository servicioRepository, ServicioMapper servicioMapper) {
        this.servicioRepository = servicioRepository;
        this.servicioMapper = servicioMapper;
    }
    @Override
    @Transactional
    public ServicioResponseDTO crearServicio(CrearServicioDTO crearDto) {
        Servicio nuevoServicio = servicioMapper.toDomain(crearDto);
        ServicioPersistenceDTO dtoParaGuardar = servicioMapper.toPersistenceDTO(nuevoServicio);
        
        ServicioPersistenceDTO dtoGuardado = servicioRepository.guardarServicio(dtoParaGuardar);
        
        return servicioMapper.toResponseDTO(servicioMapper.toDomain(dtoGuardado));
    }

    @Override
    public ServicioResponseDTO editarServicio(Long id, EditarServicioDTO editarDto) {
       ServicioPersistenceDTO dtoExistente = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));

        Servicio servicio = servicioMapper.toDomain(dtoExistente);
        if (editarDto.getNombre() != null) {
            servicio.setNombre(editarDto.getNombre());
        }
        if (editarDto.getDescripcion() != null) {
            servicio.setDescripcion(editarDto.getDescripcion());
        }
        if (editarDto.getRecibo() != null) {
            servicio.setRecibo(editarDto.getRecibo());
        }

        ServicioPersistenceDTO dtoParaActualizar = servicioMapper.toPersistenceDTO(servicio);
        ServicioPersistenceDTO dtoActualizado = servicioRepository.guardarServicio(dtoParaActualizar);

        return servicioMapper.toResponseDTO(servicioMapper.toDomain(dtoActualizado));
    }

    @Override
    public void eliminarServicio(Long id) {
        if (!servicioRepository.findById(id).isPresent()) {
            throw new RuntimeException("Servicio no encontrado: " + id);
        }
        servicioRepository.eliminarServicio(id);
    }

    @Override
    public ServicioResponseDTO obtenerServicio(Long id) {
        return servicioRepository.findById(id)
                .map(servicioMapper::toDomain)
                .map(servicioMapper::toResponseDTO) 
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));
    }
}

