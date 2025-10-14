package com.backend.bcp.app.Comprobante.Infraestructure.adapters.out;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Aplication.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
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
    public void guardarComprobante(Comprobante comprobante) {
        springDataComprobanteRepository.save(mapper.toEntity(comprobante));
    }

}
