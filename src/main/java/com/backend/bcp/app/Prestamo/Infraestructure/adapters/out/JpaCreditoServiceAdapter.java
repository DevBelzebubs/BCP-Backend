package com.backend.bcp.app.Prestamo.Infraestructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Prestamo.Application.mapper.PrestamoMapper;
import com.backend.bcp.app.Prestamo.Application.ports.out.CreditoService;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;
import com.backend.bcp.app.Prestamo.Infraestructure.repo.SpringDataPrestamoRepository;

@Service
public class JpaCreditoServiceAdapter implements CreditoService {
    private final SpringDataPrestamoRepository prestamoRepository;
    private final PrestamoMapper prestamoMapper;
    
    public JpaCreditoServiceAdapter(SpringDataPrestamoRepository prestamoRepository, PrestamoMapper prestamoMapper) {
        this.prestamoRepository = prestamoRepository;
        this.prestamoMapper = prestamoMapper;
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id).map(prestamoMapper::toDomain);
    }

    @Override
    public void guardarSolicitudCredito(Prestamo solicitud) {
        PrestamoEntity entity = prestamoMapper.toEntity(solicitud);
        prestamoRepository.save(entity);
    }

    @Override
    public List<Prestamo> findAllSolicitudes() {
        return prestamoRepository.findAll().stream().map(prestamoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Prestamo> findSolicitudesById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findSolicitudesById'");
    }

}
