package com.backend.bcp.app.Pago.Infraestructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Pago.Application.dto.in.EditarPagoDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Application.mapper.PagoPersistenceMapper;
import com.backend.bcp.app.Pago.Application.ports.out.PagoRepository;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;
import com.backend.bcp.app.Servicio.Infraestructure.repo.SpringDataServicioRepository;

@Component
public class JpaPagoRepositoryAdapter implements PagoRepository{
    private final SpringDataPagoRepository pagoRepository;
    private final PagoPersistenceMapper mapper;
    private final SpringDataServicioRepository servicioRepository;
    public JpaPagoRepositoryAdapter(SpringDataPagoRepository pagoRepository, PagoPersistenceMapper mapper,SpringDataServicioRepository servicioRepository) {
        this.pagoRepository = pagoRepository;
        this.mapper = mapper;
        this.servicioRepository = servicioRepository;
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
    @Override
    @Transactional(readOnly = true)
    public Optional<PagoPersistenceDTO> findById(Long pagoId) {
        return pagoRepository.findById(pagoId).map(mapper::toPersistenceDTO);
    }
    @Override
    public PagoPendienteDTO editarPago(Long pagoId, EditarPagoDTO editarPagoDTO) {
       PagoEntity pagoEntity = pagoRepository.findById(pagoId)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado: " + pagoId));

        if (!"PENDIENTE".equals(pagoEntity.getEstado())) {
            throw new RuntimeException("No se puede editar un pago que no esté PENDIENTE");
        }

        boolean updated = false;

        if (editarPagoDTO.getMonto() != null && editarPagoDTO.getMonto().compareTo(pagoEntity.getMonto()) != 0) {
            pagoEntity.setMonto(editarPagoDTO.getMonto());
            updated = true;
        }

        if (editarPagoDTO.getServicioId() != null) {
            if (pagoEntity instanceof PagoServicioEntity) {
                PagoServicioEntity pagoServicio = (PagoServicioEntity) pagoEntity;
                
                if (pagoServicio.getServicio() == null || !pagoServicio.getServicio().getIdServicio().equals(editarPagoDTO.getServicioId())) {
                    
                    ServicioEntity nuevoServicio = servicioRepository.findById(editarPagoDTO.getServicioId())
                        .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + editarPagoDTO.getServicioId()));
                    
                    pagoServicio.setServicio(nuevoServicio);
                    updated = true;
                }
            } else {
                throw new RuntimeException("Este pago es de tipo Préstamo y no se puede cambiar su servicio.");
            }
        }
        
        if (updated) {
            pagoEntity = pagoRepository.save(pagoEntity);
        }
        return mapper.toPagoPendienteDTO(mapper.toPersistenceDTO(pagoEntity));
    }
    @Override
    public void eliminarPago(Long pagoId) {
        PagoEntity pagoEntity = pagoRepository.findById(pagoId)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado: " + pagoId));

        if (!"PENDIENTE".equals(pagoEntity.getEstado())) {
            throw new RuntimeException("No se puede eliminar un pago que no esté PENDIENTE");
        }
        
        pagoRepository.deleteById(pagoId);
    }
    
}
