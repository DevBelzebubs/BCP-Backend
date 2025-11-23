package com.backend.bcp.app.shared.Infraestructure.adapters.out;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.AlertaRepositoryPort;
import com.backend.bcp.app.shared.Application.dto.out.AlertaPersistenceDTO;
import com.backend.bcp.app.shared.Application.mapper.AlertaMapper;
import com.backend.bcp.app.shared.Infraestructure.entity.AlertaEntity;
import com.backend.bcp.app.shared.Infraestructure.repo.SpringDataAlertaRepository;

@Component
public class JpaAlertaRepositoryAdapter implements AlertaRepositoryPort {
    private final SpringDataAlertaRepository springRepository;
    private final AlertaMapper mapper;

    public JpaAlertaRepositoryAdapter(SpringDataAlertaRepository springRepository, AlertaMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public AlertaPersistenceDTO guardarAlerta(AlertaPersistenceDTO alertaDto) {
        AlertaEntity entity = mapper.toEntity(alertaDto);
        AlertaEntity savedEntity = springRepository.save(entity);
        return mapper.toDTO(savedEntity);
    }

}
