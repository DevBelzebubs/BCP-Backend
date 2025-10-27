package com.backend.bcp.app.Usuario.Application.persistence;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoPersistenceDTO;
import com.backend.bcp.app.Reclamo.Application.ports.out.ReclamoRepository;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Shared.Application.dto.out.AlertaPersistenceDTO;
import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoAlerta;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.dto.out.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.MarcarSospechosoRequestDTO;
import com.backend.bcp.app.Usuario.Application.dto.out.OperacionSupervisionDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionSupervisionUseCase;
import com.backend.bcp.app.Usuario.Application.ports.out.Cliente.NotificacionService;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.AlertaRepositoryPort;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.GeneradorReporteGlobalPdf;

import jakarta.transaction.Transactional;

@Service
public class GestionSupervisionService implements GestionSupervisionUseCase {
    private final ReclamoRepository reclamoRepository;
    private final TransaccionRepository transaccionRepository;
    private final AlertaRepositoryPort alertaRepository;
    private final NotificacionService notificacionService;
    private final GeneradorReporteGlobalPdf reporteGlobalPdfGenerator;
    private final UserRepository userRepository;
    
public GestionSupervisionService(ReclamoRepository reclamoRepository, TransaccionRepository transaccionRepository,
            AlertaRepositoryPort alertaRepository, NotificacionService notificacionService,
            GeneradorReporteGlobalPdf reporteGlobalPdfGenerator, UserRepository userRepository) {
        this.reclamoRepository = reclamoRepository;
        this.transaccionRepository = transaccionRepository;
        this.alertaRepository = alertaRepository;
        this.notificacionService = notificacionService;
        this.reporteGlobalPdfGenerator = reporteGlobalPdfGenerator;
        this.userRepository = userRepository;
    }

    @Override
    public List<OperacionSupervisionDTO> obtenerOperacionesParaSupervisar() {
        List<ReclamoPersistenceDTO> reclamos = reclamoRepository.findAll();
        List<MovimientoAppDTO> transacciones = transaccionRepository.buscarMovimientosPorFecha(LocalDate.now());
        Stream<OperacionSupervisionDTO> reclamosStream = reclamos.stream().map(r -> new OperacionSupervisionDTO(
                r.idReclamo(),
                "RECLAMO",
                r.fechaCreacion().atStartOfDay(),
                "Reclamo Cliente ID: " + r.clienteId() + " (" + r.numeroSeguimiento() + ")",
                null,
                r.estadoReclamo().toString()));
        Stream<OperacionSupervisionDTO> transaccionesStream = transacciones.stream().map(t -> new OperacionSupervisionDTO(
                t.id(),
                "TRANSACCION",
                t.fecha(),
                "Transacción " + t.tipo() + " Cuenta ID: " + t.cuentaId(),
                t.monto(),
                t.tipo()
        ));
        return Stream.concat(reclamosStream, transaccionesStream).sorted(Comparator.comparing(OperacionSupervisionDTO::fechaHora).reversed()).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public Long marcarOperacionComoSospechosa(MarcarSospechosoRequestDTO request, Long backOfficeUserId) {
        Long adminUserId = userRepository.findByNombre("admin").map(UsuarioDTO::id).orElse(null);
        if (adminUserId == null) {
             System.err.println("WARN: No se encontró usuario 'admin' para notificar alerta.");
        }
        String mensaje = String.format(
                "Operación %s (ID: %d) marcada como sospechosa por BackOffice ID: %d. Justificación: %s",
                request.tipoOperacion(),
                request.operacionId(),
                backOfficeUserId,
                request.justificacion()
        );
        AlertaPersistenceDTO alertaDto = new AlertaPersistenceDTO(
            TipoAlerta.ACTIVIDAD_SOSPECHOSA,
            mensaje,
            adminUserId
        );
        AlertaPersistenceDTO alertaGuardada = alertaRepository.guardarAlerta(alertaDto);
        if (adminUserId != null) {
             notificacionService.notificarCliente(adminUserId, "¡Alerta! Operación Sospechosa: " + mensaje);
        }

        return alertaGuardada.id();
    }

    @Override
    public byte[] generarReporteGlobalOperaciones() {
        List<OperacionSupervisionDTO> todasLasOperaciones = obtenerOperacionesParaSupervisar();
        return reporteGlobalPdfGenerator.generarPdf(todasLasOperaciones);
    }

}
