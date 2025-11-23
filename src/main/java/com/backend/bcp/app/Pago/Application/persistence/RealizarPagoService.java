package com.backend.bcp.app.Pago.Application.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Application.dto.in.EditarPagoDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoRequestDTO;
import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Application.mapper.PagoPersistenceMapper;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Pago.Application.ports.out.PagoRepository;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Servicio.Application.ports.out.ServicioRepository;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.shared.Application.Security.ports.out.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RealizarPagoService implements RealizarPagoUseCase {
private final PagoRepository pagoRepository;
    private final CuentaRepository cuentaRepository;
    private final SpringDataPagoRepository springDataPagoRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final PagoPersistenceMapper mapper;
    private final SpringDataClientRepository springDataClientRepository;
    private final UserRepository userRepository;

    public RealizarPagoService(PagoRepository pagoRepository, CuentaRepository cuentaRepository,
            ServicioRepository servicioRepository, ComprobanteRepository comprobanteRepository,
            PagoPersistenceMapper mapper, SpringDataClientRepository springDataClientRepository,
            SpringDataPagoRepository springDataPagoRepository, UserRepository userRepository) {
        this.pagoRepository = pagoRepository;
        this.cuentaRepository = cuentaRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.springDataClientRepository = springDataClientRepository;
        this.springDataPagoRepository = springDataPagoRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    @Override
    public List<PagoPendienteDTO> listarPagosPendientes(String dni) {
        Long usuarioId = userRepository.findByDni(dni)
                .map(UsuarioDTO::id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));
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
    @Override
    public ComprobanteDTO realizarPagoExterno(DebitoRequestDTO debitoRequestDTO) {
        UsuarioDTO usuarioDto = userRepository.findByDni(debitoRequestDTO.dniCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DNI: " + debitoRequestDTO.dniCliente()));
        
        ClienteEntity clienteEntity = springDataClientRepository.findByIdUsuario_Id(usuarioDto.id())
            .orElseThrow(() -> new RuntimeException("Datos de Cliente no encontrados para DNI: " + debitoRequestDTO.dniCliente()));

        Cuenta cuenta = cuentaRepository.obtenerCuentasPorUsuario(usuarioDto.id()).stream().map(mapper::toCuentaDomain).filter(c -> c.getNumeroCuenta().equals(debitoRequestDTO.numeroCuentaOrigen()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cuenta " + debitoRequestDTO.numeroCuentaOrigen() + " no encontrada o no pertenece al cliente."));
        PagoEntity pagoEntity = springDataPagoRepository.findById(debitoRequestDTO.idPagoBCP())
            .orElseThrow(() -> new RuntimeException("Pago pendiente no encontrado en BCP: " + debitoRequestDTO.idPagoBCP()));
        if (!Objects.equals(pagoEntity.getCliente().getIdCliente(), clienteEntity.getIdCliente())) {
            throw new RuntimeException("Inconsistencia: El pago no pertenece al cliente especificado.");
        }
        if (!"PENDIENTE".equalsIgnoreCase(pagoEntity.getEstado())) {
            throw new RuntimeException("El pago ID " + pagoEntity.getIdPago() + " ya no está pendiente.");
        }
        if (pagoEntity.getMonto().compareTo(debitoRequestDTO.monto()) != 0) {
            throw new RuntimeException("Inconsistencia en el monto del pago.");
        }
        if (pagoEntity.getPayflowServiceId() == null) {
            pagoEntity.setPayflowServiceId(debitoRequestDTO.idServicioPayflow());
        }
        cuenta.retirar(debitoRequestDTO.monto());
        cuentaRepository.actualizar(cuenta);

        pagoEntity.setEstado("PAGADO");
        pagoEntity.setFecha(LocalDate.now());
        springDataPagoRepository.save(pagoEntity);

        String nombreServicio = "Pago Servicio (Payflow ID: " + debitoRequestDTO.idServicioPayflow() + ")";
        if (pagoEntity instanceof PagoServicioEntity pagoServicio && pagoServicio.getServicio() != null) {
             nombreServicio = pagoServicio.getServicio().getNombre();
        }
        Comprobante comprobanteDomain = new Comprobante();
        comprobanteDomain.setServicio(nombreServicio);
        comprobanteDomain.setMontoPagado(debitoRequestDTO.monto());
        comprobanteDomain.setFecha(LocalDate.now());
        String codigoAutorizacion = "EXT-PF-" + pagoEntity.getIdPago() + "-" + System.currentTimeMillis();
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
    

}
