package com.backend.bcp.app.Shared.Infraestructure.adapters.out.Jasper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Application.mapper.PagoPersistenceMapper;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.mapper.PrestamoMapper;
import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;
import com.backend.bcp.app.Prestamo.Infraestructure.repo.SpringDataPrestamoRepository;
import com.backend.bcp.app.Transaccion.Application.dto.out.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.mapper.TransaccionPersistenceMapper;
import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;
import com.backend.bcp.app.Transaccion.Infraestructure.repo.SpringDataTransaccionRespository;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.ReporteDiarioDataPort;

@Component
public class JpaReporteDiarioAdapter implements ReporteDiarioDataPort {
private final SpringDataTransaccionRespository transaccionRepo;
    private final SpringDataPagoRepository pagoRepo;
    private final SpringDataPrestamoRepository prestamoRepo;
    
    private final TransaccionPersistenceMapper transaccionMapper;
    private final PagoPersistenceMapper pagoMapper;
    private final PrestamoMapper prestamoMapper;
    public JpaReporteDiarioAdapter(SpringDataTransaccionRespository transaccionRepo, SpringDataPagoRepository pagoRepo,
            SpringDataPrestamoRepository prestamoRepo, TransaccionPersistenceMapper transaccionMapper,
            PagoPersistenceMapper pagoMapper, PrestamoMapper prestamoMapper) {
        this.transaccionRepo = transaccionRepo;
        this.pagoRepo = pagoRepo;
        this.prestamoRepo = prestamoRepo;
        this.transaccionMapper = transaccionMapper;
        this.pagoMapper = pagoMapper;
        this.prestamoMapper = prestamoMapper;
    }
    @Override
    public List<MovimientoPersistenceDTO> findTransaccionesByFecha(LocalDate fecha) {
        List<TransaccionEntity> entities = transaccionRepo.findByFechaBetween(
            fecha.atStartOfDay(), 
            fecha.atTime(LocalTime.MAX)
        );
        return entities.stream()
            .map(transaccionMapper::toPersistenceDTO)
            .collect(Collectors.toList());
    }
    @Override
    public List<PagoPersistenceDTO> findPagosByFecha(LocalDate fecha) {
        List<PagoEntity> entities = pagoRepo.findByFecha(fecha);
        return entities.stream()
            .map(pagoMapper::toPersistenceDTO)
            .collect(Collectors.toList());
    }
    @Override
    public List<PrestamoPersistenceDTO> findPrestamosNuevosByFecha(LocalDate fecha) {
        List<PrestamoEntity> entities = prestamoRepo.findByFechaInicio(fecha);
        return entities.stream()
            .map(prestamoMapper::entityToPersistenceDTO)
            .collect(Collectors.toList());
    }
    
}
