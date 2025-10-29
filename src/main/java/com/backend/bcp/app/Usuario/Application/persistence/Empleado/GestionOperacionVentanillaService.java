package com.backend.bcp.app.Usuario.Application.persistence.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Application.mapper.CuentaPersistenceMapper;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.DepositoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.PagoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.RetiroVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionOperacionVentanillaUseCase;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;

import jakarta.transaction.Transactional;

@Service
public class GestionOperacionVentanillaService implements GestionOperacionVentanillaUseCase {
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final SpringDataPagoRepository springDataPagoRepository;
    private final ComprobanteRepository comprobanteRepository;
    
    private final UserRepository userRepository;
    private final SpringDataClientRepository clientRepository;
    private final CuentaPersistenceMapper cuentaMapper;

    public GestionOperacionVentanillaService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository,
            SpringDataPagoRepository springDataPagoRepository, ComprobanteRepository comprobanteRepository,
            UserRepository userRepository, SpringDataClientRepository clientRepository, CuentaPersistenceMapper cuentaMapper) { // <--- CONSTRUCTOR ACTUALIZADO
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.springDataPagoRepository = springDataPagoRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.cuentaMapper = cuentaMapper;
    }

    @Override
    @Transactional
    public ComprobanteDTO registrarDepositoVentanilla(DepositoVentanillaDTO request, Long empleadoId) {
        // Lógica movida aquí
        ClienteEntity cliente = findClienteByDni(request.getClienteDni());
        Cuenta cuenta = findCuentaByNumeroAndClienteId(request.getNumeroCuenta(), cliente.getIdCliente());
        
        BigDecimal monto = request.getMonto();
        cuenta.depositar(monto);
        cuentaRepository.actualizar(cuenta);

        MovimientoAppDTO transaccionDTO = new MovimientoAppDTO(null, cuenta.getId(), "DEPOSITO", monto, LocalDateTime.now());
        transaccionRepository.guardarTransaccion(transaccionDTO);

        // Lógica de comprobante movida aquí
        return generarComprobante("Depósito en Ventanilla", monto, "DEP-V-" + System.currentTimeMillis());
    }

    @Override
    @Transactional
    public ComprobanteDTO registrarRetiroVentanilla(RetiroVentanillaDTO request, Long empleadoId) {
        // Lógica movida aquí
        ClienteEntity cliente = findClienteByDni(request.getClienteDni());
        Cuenta cuenta = findCuentaByNumeroAndClienteId(request.getNumeroCuenta(), cliente.getIdCliente());
        
        BigDecimal monto = request.getMonto();
        cuenta.retirar(monto);
        cuentaRepository.actualizar(cuenta);

        MovimientoAppDTO transaccionDTO = new MovimientoAppDTO(null, cuenta.getId(), "RETIRO", monto, LocalDateTime.now());
        transaccionRepository.guardarTransaccion(transaccionDTO);

        return generarComprobante("Retiro en Ventanilla", monto, "RET-V-" + System.currentTimeMillis());
    }

    @Override
    @Transactional
    public ComprobanteDTO registrarPagoVentanilla(PagoVentanillaDTO request, Long empleadoId) {
        ClienteEntity cliente = findClienteByDni(request.getClienteDni());
        Cuenta cuentaDebito = findCuentaByNumeroAndClienteId(request.getNumeroCuenta(), cliente.getIdCliente());
        
        PagoEntity pagoEntity = springDataPagoRepository.findById(request.getPagoId())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + request.getPagoId()));
        if (!pagoEntity.getCliente().getIdCliente().equals(cliente.getIdCliente())) {
             throw new RuntimeException("El pago ID " + request.getPagoId() + " no pertenece al cliente.");
        }
        if (!"PENDIENTE".equalsIgnoreCase(pagoEntity.getEstado())) {
             throw new RuntimeException("El pago ID " + request.getPagoId() + " no está pendiente.");
        }
        BigDecimal montoPago = pagoEntity.getMonto();

        cuentaDebito.retirar(montoPago);
        cuentaRepository.actualizar(cuentaDebito);

        MovimientoAppDTO transaccionDto = new MovimientoAppDTO(null, cuentaDebito.getId(), "RETIRO", montoPago, LocalDateTime.now());
        transaccionRepository.guardarTransaccion(transaccionDto);

        pagoEntity.setEstado("PAGADO");
        pagoEntity.setFecha(LocalDate.now());
        springDataPagoRepository.save(pagoEntity);
        String detalleComprobante = "Pago Ventanilla (ID: " + pagoEntity.getIdPago() + ")";
        if (pagoEntity instanceof PagoServicioEntity pse && pse.getServicio() != null) {
            detalleComprobante = "Pago Servicio Ventanilla: " + pse.getServicio().getNombre();
        }

        return generarComprobante(detalleComprobante, montoPago, "PAG-V-" + pagoEntity.getIdPago());
    }


    private ClienteEntity findClienteByDni(String dni) {
        UsuarioDTO usuarioDto = userRepository.findByDni(dni)
                 .orElseThrow(() -> new RuntimeException("Cliente no identificado con DNI: " + dni));
         return clientRepository.findByIdUsuario_Id(usuarioDto.id())
                 .orElseThrow(() -> new RuntimeException("Registro interno de cliente no encontrado para DNI: " + dni));
    }
 
    private Cuenta findCuentaByNumeroAndClienteId(String numeroCuenta, Long clienteId) {
         ClienteEntity clienteEntity = clientRepository.findById(clienteId)
             .orElseThrow(() -> new RuntimeException("Registro interno de cliente no encontrado para Cliente ID: " + clienteId));
        Long usuarioId = clienteEntity.getIdUsuario().getId();
        
        List<Cuenta> cuentasCliente = cuentaRepository.obtenerCuentasPorUsuario(usuarioId)
                .stream()
                .map(cuentaMapper::toDomain) 
                .toList();
 
        Cuenta cuenta = cuentasCliente.stream()
                .filter(c -> numeroCuenta.equals(c.getNumeroCuenta()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cuenta " + numeroCuenta + " no encontrada o no pertenece al cliente ID: " + clienteId));
 
        if (!"ACTIVO".equalsIgnoreCase(cuenta.getEstadoCuenta())) {
             throw new RuntimeException("La cuenta " + numeroCuenta + " no está activa.");
        }
        return cuenta;
    }

    private ComprobanteDTO generarComprobante(String detalle, BigDecimal monto, String codigoAutorizacion) {
        Comprobante comprobanteDomain = new Comprobante();
        comprobanteDomain.setServicio(detalle);
        comprobanteDomain.setMontoPagado(monto);
        comprobanteDomain.setFecha(LocalDate.now());
        comprobanteDomain.setCodigoAutorizacion(codigoAutorizacion);

        Comprobante comprobanteGuardado = comprobanteRepository.guardarComprobante(comprobanteDomain); // <--- Solo usamos guardar

        return new ComprobanteDTO(
                comprobanteGuardado.getId(),
                comprobanteGuardado.getServicio(),
                comprobanteGuardado.getMontoPagado(),
                comprobanteGuardado.getFecha(),
                comprobanteGuardado.getCodigoAutorizacion()
        );
    }

}
