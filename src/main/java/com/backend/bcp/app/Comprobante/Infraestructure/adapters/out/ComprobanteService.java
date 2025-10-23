package com.backend.bcp.app.Comprobante.Infraestructure.adapters.out;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Comprobante.Infraestructure.entity.ComprobanteEntity;
import com.backend.bcp.app.Comprobante.Infraestructure.repo.SpringDataComprobanteRepository;

@Service
public class ComprobanteService implements ComprobanteRepository {
    SpringDataComprobanteRepository springDataComprobanteRepository;
    ComprobanteMapper mapper;
    
    public ComprobanteService(SpringDataComprobanteRepository springDataComprobanteRepository,
            ComprobanteMapper mapper) {
        this.springDataComprobanteRepository = springDataComprobanteRepository;
        this.mapper = mapper;
    }
    

    @Override
    public Comprobante guardarComprobante(Comprobante comprobante) {
        ComprobanteEntity entity = mapper.toEntity(comprobante);
        ComprobanteEntity savedEntity = springDataComprobanteRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

}
