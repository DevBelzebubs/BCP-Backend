package com.backend.bcp.app.Transaccion.Infraestructure.adapters.out;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.mapper.TransaccionPersistenceMapper;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
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
    public List<MovimientoAppDTO> buscarUltimosMovimientos(Long cuentaId) {
        List<TransaccionEntity> entities = transaccionRepository.findTop10ByCuenta_IdCuentaOrderByFechaDesc(cuentaId);
        return entities.stream()
            .map(mapper::mapEntityToAppDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void guardarTransaccion(MovimientoAppDTO movimientoAppDTO) {
        if (movimientoAppDTO == null) return;
        
        TransaccionEntity entity = mapper.mapAppDTOToEntity(movimientoAppDTO);
        transaccionRepository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public List<MovimientoAppDTO> buscarMovimientosPorFecha(LocalDate fecha) {
        LocalDateTime inicioDelDia = fecha.atStartOfDay();
        LocalDateTime finDelDia = fecha.atTime(LocalTime.MAX);
        List<TransaccionEntity> entities = transaccionRepository.findByFechaBetween(inicioDelDia, finDelDia);
        return entities.stream()
            .map(mapper::mapEntityToAppDTO)
            .collect(Collectors.toList());
    }

}
