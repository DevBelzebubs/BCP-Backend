package com.backend.bcp.app.Pago.Application.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Application.mapper.CuentaPersistenceMapper;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoDirectoRequestDTO;
import com.backend.bcp.app.Pago.Application.ports.in.DebitoDirectoS2SUseCase;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;

import jakarta.transaction.Transactional;

@Service
public class DebitoDirectoS2SService implements DebitoDirectoS2SUseCase {
    private final UserRepository userRepository;
    private final CuentaRepository cuentaRepository;
    private final CuentaPersistenceMapper cuentaMapper;
    private final TransaccionRepository transaccionRepository;
    private final ComprobanteRepository comprobanteRepository;
    public DebitoDirectoS2SService(UserRepository userRepository, CuentaRepository cuentaRepository,
            CuentaPersistenceMapper cuentaMapper, TransaccionRepository transaccionRepository,
            ComprobanteRepository comprobanteRepository) {
        this.userRepository = userRepository;
        this.cuentaRepository = cuentaRepository;
        this.cuentaMapper = cuentaMapper;
        this.transaccionRepository = transaccionRepository;
        this.comprobanteRepository = comprobanteRepository;
    }
    @Override
    @Transactional
    public ComprobanteDTO ejecutarDebito(DebitoDirectoRequestDTO request) {
        UsuarioDTO usuarioDTO = userRepository.findByDni(request.dniCliente()).orElseThrow(() -> new RuntimeException("No se encontró usuario con dni" + request.dniCliente()));
        Cuenta cuenta = cuentaRepository.obtenerCuentasPorUsuario(usuarioDTO.id()).stream()
            .map(cuentaMapper::toDomain)
            .filter(c -> c.getNumeroCuenta().equals(request.numeroCuentaOrigen()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cuenta " + request.numeroCuentaOrigen() + " no encontrada o no pertenece al cliente."));
        cuenta.retirar(request.monto());
        cuentaRepository.actualizar(cuenta);
        MovimientoAppDTO retiroDto = new MovimientoAppDTO(null, cuenta.getId(), "RETIRO", request.monto(), LocalDateTime.now());
        transaccionRepository.guardarTransaccion(retiroDto);

        Comprobante comprobante = new Comprobante();
        comprobante.setServicio(request.descripcionCompra());
        comprobante.setMontoPagado(request.monto());
        comprobante.setFecha(LocalDate.now());
        comprobante.setCodigoAutorizacion("PF-DEBIT-" + System.currentTimeMillis());
        Comprobante comprobanteGuardado = comprobanteRepository.guardarComprobante(comprobante);
        
        return new ComprobanteDTO(
            comprobanteGuardado.getId(),
            comprobanteGuardado.getServicio(),
            comprobanteGuardado.getMontoPagado(),
            comprobanteGuardado.getFecha(),
            comprobanteGuardado.getCodigoAutorizacion()
        );
    }
    
}
