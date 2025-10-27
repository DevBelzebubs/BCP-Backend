package com.backend.bcp.app.Pago.Application.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Application.dto.in.EditarPagoDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Application.mapper.PagoPersistenceMapper;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Pago.Application.ports.out.PagoRepository;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;

import jakarta.transaction.Transactional;

@Service
public class RealizarPagoService implements RealizarPagoUseCase {
private final PagoRepository pagoRepository;
    private final CuentaRepository cuentaRepository;
    private final SpringDataPagoRepository springDataPagoRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final PagoPersistenceMapper mapper;
    private final SpringDataClientRepository springDataClientRepository;

    public RealizarPagoService(PagoRepository pagoRepository, CuentaRepository cuentaRepository,
            ServicioRepository servicioRepository, ComprobanteRepository comprobanteRepository,
            PagoPersistenceMapper mapper, SpringDataClientRepository springDataClientRepository,
            SpringDataPagoRepository springDataPagoRepository) {
        this.pagoRepository = pagoRepository;
        this.cuentaRepository = cuentaRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.springDataClientRepository = springDataClientRepository;
        this.springDataPagoRepository = springDataPagoRepository;
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
    @Transactional
    public ComprobanteDTO realizarPago(Long cuentaId, Long pagoId) {
        Cuenta cuenta = cuentaRepository.obtenerPorId(cuentaId)
                .map(mapper::toCuentaDomain)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + cuentaId));

        PagoEntity pagoEntity = springDataPagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago pendiente no encontrado: " + pagoId));

        Long clienteIdObtenidoDeCuenta = cuenta.getCliente().getId(); 

        if (!pagoEntity.getCliente().getIdCliente().equals(clienteIdObtenidoDeCuenta)) {
             throw new RuntimeException("El pago ID " + pagoId + " no pertenece al cliente asociado a la cuenta ID " + cuentaId
             + ". PagoClienteID: " + pagoEntity.getCliente().getIdCliente() + ", CuentaClienteID(erróneo): " + clienteIdObtenidoDeCuenta);
        }

        if (!"PENDIENTE".equalsIgnoreCase(pagoEntity.getEstado())) {
            throw new RuntimeException("El pago ID " + pagoId + " no está pendiente.");
        }

        BigDecimal montoAPagar = pagoEntity.getMonto();
        String nombreServicioOPrestamo = "Pago General";
        if (pagoEntity instanceof PagoServicioEntity pagoServicioEntity && pagoServicioEntity.getServicio() != null) {
             nombreServicioOPrestamo = pagoServicioEntity.getServicio().getNombre();
        }

        cuenta.retirar(montoAPagar);
        cuentaRepository.actualizar(cuenta);

        pagoEntity.setEstado("PAGADO");
        pagoEntity.setFecha(LocalDate.now());
        springDataPagoRepository.save(pagoEntity);

        Comprobante comprobanteDomain = new Comprobante();
        comprobanteDomain.setServicio(nombreServicioOPrestamo);
        comprobanteDomain.setMontoPagado(montoAPagar);
        comprobanteDomain.setFecha(LocalDate.now());
        String codigoAutorizacion = "CMP-PAGO-" + pagoId + "-" + System.currentTimeMillis();
        comprobanteDomain.setCodigoAutorizacion(codigoAutorizacion);

        Comprobante comprobanteGuardado = comprobanteRepository.guardarComprobante(comprobanteDomain);

        return new ComprobanteDTO(
                comprobanteGuardado.getId(),
                comprobanteGuardado.getServicio(),
                comprobanteGuardado.getMontoPagado(),
                comprobanteGuardado.getFecha(),
                comprobanteGuardado.getCodigoAutorizacion()
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
