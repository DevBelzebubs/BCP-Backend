package com.backend.bcp.app.Reclamo.Application.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoPersistenceDTO;
import com.backend.bcp.app.Reclamo.Application.mapper.ReclamoMapper;
import com.backend.bcp.app.Reclamo.Application.ports.out.ReclamoRepository;
import com.backend.bcp.app.Reclamo.Infraestructure.entity.ReclamoEntity;
import com.backend.bcp.app.Reclamo.Infraestructure.repo.SpringDataReclamoRepository;


@Service
public class GestionReclamosServiceImpl implements ReclamoRepository {
private final SpringDataReclamoRepository repository;    
private final ReclamoMapper reclamoMapper;
    public GestionReclamosServiceImpl(SpringDataReclamoRepository repository, ReclamoMapper reclamoMapper) {
        this.repository = repository;
        this.reclamoMapper = reclamoMapper;
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<ReclamoPersistenceDTO> findById(Long id) {
        return repository.findById(id).map(reclamoMapper::entityToPersistenceDto);
    }
    @Override
    @Transactional
    public ReclamoPersistenceDTO guardarReclamo(ReclamoPersistenceDTO reclamo) {
       ReclamoEntity entity = reclamoMapper.persistenceDtoToEntity(reclamo);
       ReclamoEntity savedEntity = repository.save(entity);
       return reclamoMapper.entityToPersistenceDto(savedEntity);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ReclamoPersistenceDTO> findAll() {
        return repository.findAll().stream().map(reclamoMapper::entityToPersistenceDto).collect(Collectors.toList());
    }
   
}
