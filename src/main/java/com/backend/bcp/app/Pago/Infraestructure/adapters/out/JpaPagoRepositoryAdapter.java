package com.backend.bcp.app.Pago.Infraestructure.adapters.out;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Pago.Aplication.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Aplication.dto.out.PagoPersistenceMapper;
import com.backend.bcp.app.Pago.Aplication.ports.out.PagoRepository;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;

@Component
public class JpaPagoRepositoryAdapter implements PagoRepository{
    private final SpringDataPagoRepository pagoRepository;
    private final PagoPersistenceMapper mapper;
    public JpaPagoRepositoryAdapter(SpringDataPagoRepository pagoRepository, PagoPersistenceMapper mapper) {
        this.pagoRepository = pagoRepository;
        this.mapper = mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<PagoPersistenceDTO> obtenerPendientesPorUsuario(Long usuarioId) {
        List<PagoEntity> pendientes = pagoRepository.findPendientesByClienteId(usuarioId);
        return pendientes.stream().map(mapper::toPersistenceDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void registrarPago(Pago pago) {
        pagoRepository.save(mapper.toEntity(pago));
    }
    
}
