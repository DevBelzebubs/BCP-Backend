package com.backend.bcp.app.Servicio.Infraestructure.adapters.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Application.dto.out.ServicioMapper;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
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
    public Optional<ServicioPersistenceDTO> obtenerServicioPorId(Long servicioId) {
       return springDataServicioRepository.findById(servicioId).map(mapper::toPersistenceDTO);
    }

}
