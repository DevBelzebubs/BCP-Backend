package com.backend.bcp.app.Cuenta.Application.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Application.ports.out.GeneradorEstadoCuentaPdf;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.PendingTransferDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.OtpService;
import com.backend.bcp.app.Transaccion.Application.ports.out.PendingTransferRepository;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Usuario.Domain.Cliente;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class GestionCuentaService implements GestionCuentaUseCase {
private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final GeneradorEstadoCuentaPdf generadorPdf;
    private final OtpService otpService;
    private final ComprobanteRepository comprobanteRepository;
    private final PendingTransferRepository pendingTransferRepository;

    public GestionCuentaService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository,
            GeneradorEstadoCuentaPdf generadorPdf, OtpService otpService, ComprobanteRepository comprobanteRepository, PendingTransferRepository pendingTransferRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.generadorPdf = generadorPdf;
        this.otpService = otpService;
        this.comprobanteRepository = comprobanteRepository;
        this.pendingTransferRepository = pendingTransferRepository;
    }
    private Cuenta toDomain(CuentaPersistenceDTO dto){
        if (dto == null) throw new RuntimeException("Cuenta no encontrada.");

        Cliente cliente = new Cliente();
        cliente.setId(dto.cliente().id());
        return new Cuenta(
            dto.id(), cliente, dto.tipo(), dto.estadoCuenta(), dto.numeroCuenta(), dto.saldo()
        );
    }
    
    private CuentaDTO toPresentationDTO(CuentaPersistenceDTO dto) {
        return new CuentaDTO(dto.id(), dto.tipo(), dto.numeroCuenta(), dto.saldo());
    }

    private MovimientoDTO toMovimientoDTO(MovimientoPersistenceDTO dto) {
        return new MovimientoDTO(dto.id(), dto.tipo(), dto.monto(), dto.fecha());
    }
    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId) {
        List<CuentaPersistenceDTO> dtos = cuentaRepository.obtenerCuentasPorUsuario(usuarioId);
        return dtos.stream()
            .map(this::toPresentationDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId) {
        CuentaPersistenceDTO cuentaDto = cuentaRepository.obtenerPorId(cuentaId).orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        List<MovimientoPersistenceDTO> movimientoDTOs = transaccionRepository.buscarUltimosMovimientos(cuentaId);
         List<MovimientoDTO> movimientos = movimientoDTOs.stream()
            .map(this::toMovimientoDTO)
            .collect(Collectors.toList());
            return new DetalleCuentaDTO(
            cuentaDto.id(),
            cuentaDto.tipo(), 
            cuentaDto.saldo(),
            movimientos
        );
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generarEstadoCuentaPdf(Long cuentaId) {
        CuentaPersistenceDTO cuentaDto = cuentaRepository.obtenerPorId(cuentaId).orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        List<MovimientoPersistenceDTO> movimientoDTOs = transaccionRepository.buscarUltimosMovimientos(cuentaId);

        Cuenta cuentaDomain = toDomain(cuentaDto);

        List<Transaccion> movimientosDomain = movimientoDTOs.stream()
            .map(dto -> new Transaccion(
                dto.id(), cuentaDomain, dto.tipo(), dto.monto(), dto.fecha()
            ))
            .collect(Collectors.toList());
        return generadorPdf.generarPdf(cuentaDomain, movimientosDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public void iniciarTransferencia(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto) {
        // 1. Obtener Cuentas de Dominio
        Cuenta cuentaOrigen = cuentaRepository.obtenerPorId(idCuentaOrigen)
            .map(this::toDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada."));

        // 2. Validar saldo con la lógica de dominio (lanza excepción si es insuficiente)
        cuentaOrigen.retirar(monto); 
        
        // 3. Crear DTO Pendiente para almacenar el estado
        Long clienteId = cuentaOrigen.getCliente().getId();
        PendingTransferDTO pendingDTO = new PendingTransferDTO(
            clienteId, idCuentaOrigen, idCuentaDestino, monto
        );
        // 4. Guardar la transferencia pendiente y solicitar OTP
        pendingTransferRepository.savePendingTransfer(pendingDTO); 
        otpService.generarEnviarOtp(clienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public ComprobanteDTO confirmarTransferencia(String codigoOTP) {
        PendingTransferDTO pendingDTO = pendingTransferRepository.getTransferByOtpKey(codigoOTP)
            .orElseThrow(() -> new RuntimeException("OTP inválido o transferencia expirada. (A1)"));
            if (!otpService.validarOtp(pendingDTO.idCliente(), codigoOTP)) {
            // El Use Case debe manejar los reintentos (A1), pero la validación simple está aquí.
            throw new RuntimeException("Código OTP inválido."); 
        }
        Cuenta cuentaOrigen = cuentaRepository.obtenerPorId(pendingDTO.idCuentaOrigen())
            .map(this::toDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada."));
        
        Cuenta cuentaDestino = cuentaRepository.obtenerPorId(pendingDTO.idCuentaDestino())
            .map(this::toDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada."));
        
        BigDecimal monto = pendingDTO.monto();
        LocalDateTime now = LocalDateTime.now();

        cuentaOrigen.retirar(monto);
        cuentaDestino.depositar(monto);

        cuentaRepository.actualizar(cuentaOrigen);
        cuentaRepository.actualizar(cuentaDestino);

        Transaccion retiro = new Transaccion(null, cuentaOrigen, "RETIRO", monto, now);
        Transaccion deposito = new Transaccion(null, cuentaDestino, "DEPOSITO", monto, now);

        transaccionRepository.guardarTransaccion(retiro);
        transaccionRepository.guardarTransaccion(deposito);

        pendingTransferRepository.deleteTransfer(codigoOTP);

        Comprobante comprobanteDomain = new Comprobante();

        comprobanteDomain.setServicio("Transferencia de Fondos");
        comprobanteDomain.setMontoPagado(monto);
        comprobanteDomain.setFecha(LocalDate.now());
        comprobanteDomain.setCodigoAutorizacion("TRX-" + System.currentTimeMillis());

        comprobanteRepository.guardarComprobante(comprobanteDomain);

        return new ComprobanteDTO(
            null, 
            comprobanteDomain.getServicio(),
            comprobanteDomain.getMontoPagado(), 
            comprobanteDomain.getFecha(), 
            comprobanteDomain.getCodigoAutorizacion()
        );
    }

}
