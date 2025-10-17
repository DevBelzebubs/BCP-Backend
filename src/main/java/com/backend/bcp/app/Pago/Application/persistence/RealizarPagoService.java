package com.backend.bcp.app.Pago.Application.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Pago.Application.ports.out.PagoRepository;
import com.backend.bcp.app.Pago.Domain.PagoServicio;
import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Usuario.Domain.Cliente;

@Service
public class RealizarPagoService implements RealizarPagoUseCase {
private final PagoRepository pagoRepository;
    private final CuentaRepository cuentaRepository;
    private final ServicioRepository servicioRepository;
    private final ComprobanteRepository comprobanteRepository;

    public RealizarPagoService(PagoRepository pagoRepository, CuentaRepository cuentaRepository,
            ServicioRepository servicioRepository, ComprobanteRepository comprobanteRepository) {
        this.pagoRepository = pagoRepository;
        this.cuentaRepository = cuentaRepository;
        this.servicioRepository = servicioRepository;
        this.comprobanteRepository = comprobanteRepository;
    }
    private Cuenta toCuentaDomain(CuentaPersistenceDTO dto) {
        if (dto == null) throw new RuntimeException("Cuenta no encontrada.");
        Cliente cliente = new Cliente();
        if (dto.cliente() != null) cliente.setId(dto.cliente().id());
        
        return new Cuenta(dto.id(), cliente, dto.tipo(), dto.estadoCuenta(), dto.numeroCuenta(), dto.saldo());
    }
    private Servicio toServicioDomain(ServicioPersistenceDTO dto) {
        // Servicio Domain constructor: Servicio(Long idServicio, String nombre, String descripcion, BigDecimal recibo)
        return new Servicio(dto.id(), dto.nombre(), dto.descripcion(), dto.recibo()); 
    }
    private PagoPendienteDTO toPagoPendienteDTO(PagoPersistenceDTO dto) {
        // Esta función requiere buscar el nombre del servicio, pero para evitar un fetch N+1,
        // se usa un MOCK que incluye el ID.
        String nombreItemMock = (dto.servicioId() != null) 
                                ? "Servicio ID: " + dto.servicioId() 
                                : "Préstamo ID: " + dto.prestamoId();
        
        return new PagoPendienteDTO(
            dto.id(), 
            nombreItemMock,
            dto.monto()
        );
    }
    @Override
    public List<PagoPendienteDTO> listarPagosPendientes(Long usuarioId) {
        List<PagoPersistenceDTO> pendientes = pagoRepository.obtenerPendientesPorUsuario(usuarioId);
        
        return pendientes.stream()
            .map(this::toPagoPendienteDTO)
            .collect(Collectors.toList());
    }
    @Override
    public ComprobanteDTO realizarPago(Long cuentaId, Long servicioId, BigDecimal monto) {
        Cuenta cuenta = cuentaRepository.obtenerPorId(cuentaId)
            .map(this::toCuentaDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada."));
        ServicioPersistenceDTO servicioDto = servicioRepository.obtenerServicioPorId(servicioId)
            .orElseThrow(() -> new RuntimeException("Servicio/Préstamo no encontrado: " + servicioId));
        Servicio servicio = toServicioDomain(servicioDto);
        cuenta.retirar(monto); // El dominio Cuenta valida fondos insuficientes.
        cuentaRepository.actualizar(cuenta);
        PagoServicio pago = new PagoServicio(
            null, // ID nulo para autogeneración
            monto, 
            LocalDate.now(), 
            servicio,
            "PAGADO",
            cuenta.getCliente().getId()
        );
        pagoRepository.registrarPago(pago);
        Comprobante comprobanteDomain = new Comprobante();

        comprobanteDomain.setServicio(servicio.getNombre()); 
        comprobanteDomain.setMontoPagado(monto);
        comprobanteDomain.setFecha(LocalDate.now());
        
        // Generar Código de Autorización (Simulación, pero dinámica)
        String codigoAutorizacion = "CMP-PAGO-" + System.currentTimeMillis();
        comprobanteDomain.setCodigoAutorizacion(codigoAutorizacion);
        
        // 6. Persistir Comprobante Domain
        // El repositorio debe devolver el objeto con el ID generado (IdPago)
        comprobanteRepository.guardarComprobante(comprobanteDomain); 

        return new ComprobanteDTO(
            comprobanteDomain.getId(), // Si la persistencia actualiza el ID
            comprobanteDomain.getServicio(), 
            comprobanteDomain.getMontoPagado(), 
            comprobanteDomain.getFecha(), 
            comprobanteDomain.getCodigoAutorizacion()
        );
    }
    

}
