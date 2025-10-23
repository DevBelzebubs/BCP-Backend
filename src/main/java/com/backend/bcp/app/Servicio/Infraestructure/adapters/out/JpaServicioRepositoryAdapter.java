package com.backend.bcp.app.Servicio.Infraestructure.adapters.out;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Application.mapper.ServicioMapper;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;
import com.backend.bcp.app.Servicio.Infraestructure.repo.SpringDataServicioRepository;

@Component
public class JpaServicioRepositoryAdapter implements ServicioRepository {
    private final SpringDataServicioRepository springDataServicioRepository;
    private final ServicioMapper mapper;
    
    public JpaServicioRepositoryAdapter(SpringDataServicioRepository springDataServicioRepository,
            ServicioMapper mapper) {
        this.springDataServicioRepository = springDataServicioRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicioPersistenceDTO> findById(Long servicioId) {
            return springDataServicioRepository.findById(servicioId).map(mapper::toPersistenceDTO);

    }

    @Override
    @Transactional
    public ServicioPersistenceDTO guardarServicio(ServicioPersistenceDTO servicioDTO) {
        Servicio domain = mapper.toDomain(servicioDTO);
        ServicioEntity entity = mapper.toEntity(domain);
        
        ServicioEntity entidadGuardada = springDataServicioRepository.save(entity);
        return mapper.toPersistenceDTO(entidadGuardada);
    }

    @Override
    @Transactional
    public void eliminarServicio(Long servicioId) {
        springDataServicioRepository.deleteById(servicioId);
    }

}
