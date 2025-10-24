package com.backend.bcp.app.Transaccion.Infraestructure.adapters.out;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.mapper.TransaccionPersistenceMapper;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;
import com.backend.bcp.app.Transaccion.Infraestructure.repo.SpringDataTransaccionRespository;

@Component
public class JpaTransaccionRepositoryAdapter implements TransaccionRepository {
    private final SpringDataTransaccionRespository transaccionRepository;
    private final TransaccionPersistenceMapper mapper;

    public JpaTransaccionRepositoryAdapter(SpringDataTransaccionRespository transaccionRepository, 
                                           TransaccionPersistenceMapper mapper) {
        this.transaccionRepository = transaccionRepository;
        this.mapper = mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<MovimientoPersistenceDTO> buscarUltimosMovimientos(Long cuentaId) {
        List<TransaccionEntity> entities = transaccionRepository.findTop10ByCuenta_IdCuentaOrderByFechaDesc(cuentaId);
        return entities.stream()
            .map(mapper::toPersistenceDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public void guardarTransaccion(Transaccion transaccion) {
        if (transaccion == null) return;
        
        transaccionRepository.save(mapper.toEntity(transaccion));
    }
    @Override
    public List<MovimientoPersistenceDTO> buscarMovimientosPorFecha(LocalDate fecha) {
        return transaccionRepository.findByFecha(fecha).stream()
                .map(mapper::toPersistenceDTO)
                .collect(Collectors.toList());
    }

}
