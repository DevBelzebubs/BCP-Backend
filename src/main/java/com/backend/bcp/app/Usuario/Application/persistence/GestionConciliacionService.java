package com.backend.bcp.app.Usuario.Application.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.backend.bcp.app.Shared.Application.dto.out.AlertaPersistenceDTO;
import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoAlerta;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.ConciliacionRequestDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.ConciliacionResultDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.OperacionInterbancariaDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionarConciliacionUseCase;
import com.backend.bcp.app.Usuario.Application.ports.out.Cliente.NotificacionService;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.AlertaRepositoryPort;

import jakarta.transaction.Transactional;

public class GestionConciliacionService implements GestionarConciliacionUseCase {
        private final TransaccionRepository transaccionRepository; 
        private final AlertaRepositoryPort alertaRepository;     
        private final NotificacionService notificacionService;

    public GestionConciliacionService(TransaccionRepository transaccionRepository, AlertaRepositoryPort alertaRepository, NotificacionService notificacionService) {
        this.transaccionRepository = transaccionRepository;
        this.alertaRepository = alertaRepository;
        this.notificacionService = notificacionService;
    }

    @Override
    @Transactional
    public ConciliacionResultDTO procesarConciliacion(ConciliacionRequestDTO request, Long backOfficeUserId) {
        LocalDate fecha = request.fechaConciliacion();

        BigDecimal sumaExterna = request.operaciones().stream()
                .map(OperacionInterbancariaDTO::monto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<MovimientoPersistenceDTO> internos = transaccionRepository.buscarMovimientosPorFecha(fecha);
        BigDecimal sumaInterna = internos.stream()
                .map(MovimientoPersistenceDTO::monto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sumaExterna.compareTo(sumaInterna) != 0) {
            String mensajeAlerta = String.format(
                    "Diferencia en conciliación del %s. Suma externa: S/ %s, Suma interna: S/ %s.",
                    fecha.toString(), sumaExterna.toPlainString(), sumaInterna.toPlainString()
            );
        AlertaPersistenceDTO alertaDto = new AlertaPersistenceDTO(
                TipoAlerta.CONCILIACION_DIFERENCIA,
                mensajeAlerta, 
                backOfficeUserId
            );
            AlertaPersistenceDTO alertaGuardadaDto = alertaRepository.guardarAlerta(alertaDto);

            notificacionService.notificarCliente(backOfficeUserId, "¡Alerta de Conciliación! " + mensajeAlerta);

            return new ConciliacionResultDTO("ALERTA_GENERADA", mensajeAlerta, alertaGuardadaDto.id());
        }else{
            return new ConciliacionResultDTO("OK", "Conciliación procesada sin diferencias.", null);
        }
    }
}
