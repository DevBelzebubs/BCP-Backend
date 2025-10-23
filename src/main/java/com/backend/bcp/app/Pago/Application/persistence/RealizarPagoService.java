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
import com.backend.bcp.app.Pago.Application.dto.in.EditarPagoDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceMapper;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Pago.Application.ports.out.PagoRepository;
import com.backend.bcp.app.Pago.Domain.PagoServicio;
import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;

@Service
public class RealizarPagoService implements RealizarPagoUseCase {
private final PagoRepository pagoRepository;
    private final CuentaRepository cuentaRepository;
    private final ServicioRepository servicioRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final PagoPersistenceMapper mapper;
    private final SpringDataClientRepository springDataClientRepository;

    public RealizarPagoService(PagoRepository pagoRepository, CuentaRepository cuentaRepository,
            ServicioRepository servicioRepository, ComprobanteRepository comprobanteRepository,
            PagoPersistenceMapper mapper,SpringDataClientRepository springDataClientRepository) {
        this.pagoRepository = pagoRepository;
        this.cuentaRepository = cuentaRepository;
        this.servicioRepository = servicioRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.springDataClientRepository = springDataClientRepository;
        this.mapper = mapper;
    }
    @Override
    public List<PagoPendienteDTO> listarPagosPendientes(Long usuarioId) {
        List<PagoPersistenceDTO> pendientes = pagoRepository.obtenerPendientesPorUsuario(usuarioId);
        return pendientes.stream()
            .map(mapper::toPagoPendienteDTO)
            .collect(Collectors.toList());
    }
    @Override
    public ComprobanteDTO realizarPago(Long cuentaId, Long servicioId, BigDecimal monto) {
        Cuenta cuenta = cuentaRepository.obtenerPorId(cuentaId)
            .map(mapper::toCuentaDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada."));
        ServicioPersistenceDTO servicioDto = servicioRepository.findById(servicioId)
            .orElseThrow(() -> new RuntimeException("Servicio/Préstamo no encontrado: " + servicioId));
        Servicio servicio = mapper.toServicioDomain(servicioDto);
        cuenta.retirar(monto);
        cuentaRepository.actualizar(cuenta);
        Long usuarioId = cuenta.getCliente().getId();
        ClienteEntity clienteEntity = springDataClientRepository.findByIdUsuario_Id(usuarioId)
            .orElseThrow(() -> new RuntimeException("Error fatal: No se encontró la entidad Cliente para el usuario: " + usuarioId));
        Long idCliente = clienteEntity.getIdCliente();
        PagoServicio pago = new PagoServicio(
            null,
            monto, 
            LocalDate.now(), 
            servicio,
            "PAGADO",
            idCliente
        );
        pagoRepository.registrarPago(pago);
        Comprobante comprobanteDomain = new Comprobante();

        comprobanteDomain.setServicio(servicio.getNombre()); 
        comprobanteDomain.setMontoPagado(monto);
        comprobanteDomain.setFecha(LocalDate.now());
        
        String codigoAutorizacion = "CMP-PAGO-" + System.currentTimeMillis();
        comprobanteDomain.setCodigoAutorizacion(codigoAutorizacion);
        comprobanteRepository.guardarComprobante(comprobanteDomain); 

        return new ComprobanteDTO(
            comprobanteDomain.getId(),
            comprobanteDomain.getServicio(), 
            comprobanteDomain.getMontoPagado(), 
            comprobanteDomain.getFecha(), 
            comprobanteDomain.getCodigoAutorizacion()
        );
    }
    @Override
    public PagoPendienteDTO editarPago(Long pagoId, EditarPagoDTO editarPagoDTO) {
        return pagoRepository.editarPago(pagoId, editarPagoDTO);
    }
    @Override
    public void eliminarPago(Long pagoId) {
        pagoRepository.eliminarPago(pagoId);
    }
    

}
