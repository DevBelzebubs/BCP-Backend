package com.backend.bcp.app.Cuenta.Aplication.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.bcp.app.Comprobante.Aplication.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Aplication.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Cuenta.Aplication.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Aplication.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Aplication.ports.in.GestionCuentaUseCase;
import com.backend.bcp.app.Cuenta.Aplication.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Aplication.ports.out.GeneradorEstadoCuentaPdf;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Transaccion.Aplication.dto.in.MovimientoDTO;
import com.backend.bcp.app.Transaccion.Aplication.ports.out.OtpService;
import com.backend.bcp.app.Transaccion.Aplication.ports.out.TransaccionRepository;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Usuario.Domain.Cliente;

import org.springframework.transaction.annotation.Transactional;

public class GestionCuentaService implements GestionCuentaUseCase {
private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final GeneradorEstadoCuentaPdf generadorPdf;
    private final OtpService otpService;
    private final ComprobanteRepository comprobanteRepository;

    public GestionCuentaService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository,
            GeneradorEstadoCuentaPdf generadorPdf, OtpService otpService, ComprobanteRepository comprobanteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.generadorPdf = generadorPdf;
        this.otpService = otpService;
        this.comprobanteRepository = comprobanteRepository;
    }
    private Cuenta toDomain(CuentaDTO dto){
        if (dto == null) throw new RuntimeException("Cuenta no encontrada.");

        Cliente cliente = new Cliente();
        cliente.setId(dto.cliente().id());
        return new Cuenta(
            dto.id(), cliente, dto.tipo(), dto.estadoCuenta(), dto.numeroCuenta(), dto.saldo()
        );
    }
    
    private CuentaDTO toPresentationDTO(CuentaDTO dto) {
        return new CuentaDTO(dto.id(), dto.tipo(), dto.numeroCuenta(), dto.saldo());
    }

    private MovimientoDTO toMovimientoDTO(MovimientoDTO dto) {
        return new MovimientoDTO(dto.id(), dto.tipo(), dto.monto(), dto.fecha());
    }
    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> listarCuentasPorUsuario(Long usuarioId) {
        List<CuentaDTO> dtos = cuentaRepository.obtenerCuentasPorUsuario(usuarioId);
        return dtos.stream()
            .map(this::toPresentationDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId) {
        CuentaDTO cuentaDto = cuentaRepository.obtenerPorId(cuentaId).orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        List<MovimientoDTO> movimientoDTOs = transaccionRepository.buscarUltimosMovimientos(cuentaId);
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
        CuentaDTO cuentaDto = cuentaRepository.obtenerPorId(cuentaId).orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        List<MovimientoDTO> movimientoDTOs = transaccionRepository.buscarUltimosMovimientos(cuentaId);

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
        Cuenta cuentaOrigen = cuentaRepository.obtenerPorId(idCuentaOrigen)
            .map(this::toDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada."));
        Cuenta cuentaDestino = cuentaRepository.obtenerPorId(idCuentaDestino)
            .map(this::toDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada."));
        cuentaOrigen.retirar(monto);
        cuentaDestino.depositar(monto);

        cuentaRepository.actualizar(cuentaOrigen);
        cuentaRepository.actualizar(cuentaDestino);

        otpService.generarEnviarOtp(cuentaOrigen.getCliente().getId());
    }

    @Override
    @Transactional(readOnly = true)
    public ComprobanteDTO confirmarTransferencia(String codigoOTP) {
        Long clienteId = 1L; 
         if (!otpService.validarOtp(clienteId, codigoOTP)) {
            throw new RuntimeException("Código OTP inválido o expirado.");
        }
        return new ComprobanteDTO(); 
    }

}
