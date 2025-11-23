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
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.out.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Application.mapper.CuentaPersistenceMapper;
import com.backend.bcp.app.Cuenta.Application.mapper.MovimientoAppPresentationMapper;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Application.ports.out.GeneradorEstadoCuentaPdf;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Cuenta.Infraestructure.repo.SpringDataCuentaRepository;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.PendingTransferDTO;
import com.backend.bcp.app.Transaccion.Application.dto.out.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.mapper.TransaccionPersistenceMapper;
import com.backend.bcp.app.Transaccion.Application.ports.out.OtpService;
import com.backend.bcp.app.Transaccion.Application.ports.out.PendingTransferRepository;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Usuario.Domain.Cliente;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.shared.Application.Security.ports.out.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestionCuentaService implements GestionCuentaUseCase {

    private final SpringDataCuentaRepository springDataCuentaRepository;
    private final SpringDataClientRepository springDataClientRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final GeneradorEstadoCuentaPdf generadorPdf;
    private final OtpService otpService;
    private final ComprobanteRepository comprobanteRepository;
    private final PendingTransferRepository pendingTransferRepository;
    private final CuentaPersistenceMapper cuentaPersistenceMapper;
    private final MovimientoAppPresentationMapper movimientoAppPresentationMapper;
    private final UserRepository userRepository;

    public GestionCuentaService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository,
            GeneradorEstadoCuentaPdf generadorPdf, OtpService otpService, ComprobanteRepository comprobanteRepository, 
            PendingTransferRepository pendingTransferRepository, SpringDataCuentaRepository springDataCuentaRepository,
            SpringDataClientRepository springDataClientRepository, CuentaPersistenceMapper cuentaPersistenceMapper,
            MovimientoAppPresentationMapper movimientoAppPresentationMapper, UserRepository userRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.generadorPdf = generadorPdf;
        this.otpService = otpService;
        this.comprobanteRepository = comprobanteRepository;
        this.pendingTransferRepository = pendingTransferRepository;
        this.springDataCuentaRepository = springDataCuentaRepository;
        this.springDataClientRepository = springDataClientRepository;
        this.cuentaPersistenceMapper = cuentaPersistenceMapper;
        this.movimientoAppPresentationMapper = movimientoAppPresentationMapper;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> listarCuentasPorUsuario(String dni) {
        Long usuarioId = userRepository.findByDni(dni)
                .map(UsuarioDTO::id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));
        List<CuentaPersistenceDTO> dtos = cuentaRepository.obtenerCuentasPorUsuario(usuarioId);
        return dtos.stream()
            .map(cuentaPersistenceMapper::toPresentationDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleCuentaDTO obtenerDetalleCuenta(Long cuentaId) {
        CuentaPersistenceDTO cuentaDto = cuentaRepository.obtenerPorId(cuentaId).orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        List<MovimientoAppDTO> movimientoAppDTOs = transaccionRepository.buscarUltimosMovimientos(cuentaId);
        List<MovimientoDTO> movimientos = movimientoAppDTOs.stream().map(movimientoAppPresentationMapper::mapAppDTOToMovimientoDTO).collect(Collectors.toList());
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
        List<MovimientoAppDTO> movimientoDTOs = transaccionRepository.buscarUltimosMovimientos(cuentaId);

        Cuenta cuentaDomain = cuentaPersistenceMapper.toDomain(cuentaDto);

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
            .map(cuentaPersistenceMapper::toDomain)
            .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada."));

        cuentaOrigen.retirar(monto); 
        
        Long clienteId = cuentaOrigen.getCliente().getId();
        PendingTransferDTO pendingDTO = new PendingTransferDTO(
            clienteId, idCuentaOrigen, idCuentaDestino, monto,0
        );
        pendingTransferRepository.savePendingTransfer(pendingDTO); 
        otpService.generarEnviarOtp(clienteId);
    }

    @Override
    @Transactional
    public ComprobanteDTO confirmarTransferencia(String dni, String codigoOTP) {
        Long clienteId = userRepository.findByDni(dni)
                .map(UsuarioDTO::id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));
        PendingTransferDTO pendingDTO = pendingTransferRepository.getTransferByOtpKey(String.valueOf(clienteId))
            .orElseThrow(() -> new RuntimeException("No se encontró ninguna transferencia pendiente para este cliente. (A1)"));
            Long idCliente = pendingDTO.idCliente();
            if (!otpService.validarOtp(clienteId, codigoOTP)) {
                final int MAX_RETRIES = 3;
                int currentRetries = pendingDTO.retries();
                if (currentRetries < MAX_RETRIES - 1) {
                    PendingTransferDTO updatedDTO = new PendingTransferDTO(idCliente,
                    pendingDTO.idCuentaOrigen(),
                    pendingDTO.idCuentaDestino(),
                    pendingDTO.monto(),
                    currentRetries + 1);
                    pendingTransferRepository.updatePendingTransfer(updatedDTO);
                    throw new RuntimeException("Código OTP inválido. Reintento " + (currentRetries + 1) + " de " + MAX_RETRIES + ". Intente de nuevo.");
                }else{
                    pendingTransferRepository.deleteTransfer(String.valueOf(idCliente));
                    throw new RuntimeException("Código OTP inválido. Límite de intentos (" + MAX_RETRIES + ") excedido. La transferencia fue cancelada.");
                }
            }   
                Cuenta cuentaOrigen = cuentaRepository.obtenerPorId(pendingDTO.idCuentaOrigen())
                .map(cuentaPersistenceMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada."));
        
                Cuenta cuentaDestino = cuentaRepository.obtenerPorId(pendingDTO.idCuentaDestino())
                .map(cuentaPersistenceMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada. (A2)"));
                BigDecimal monto = pendingDTO.monto();
                LocalDateTime now = LocalDateTime.now();

                    cuentaOrigen.retirar(monto);
                cuentaDestino.depositar(monto);

                cuentaRepository.actualizar(cuentaOrigen);
                cuentaRepository.actualizar(cuentaDestino);

                MovimientoAppDTO retiroDto = new MovimientoAppDTO(null, cuentaOrigen.getId(), "RETIRO", monto, now);
                MovimientoAppDTO depositoDto = new MovimientoAppDTO(null, cuentaDestino.getId(), "DEPOSITO", monto, now);

                transaccionRepository.guardarTransaccion(retiroDto);
                transaccionRepository.guardarTransaccion(depositoDto);

                pendingTransferRepository.deleteTransfer(String.valueOf(idCliente));

                Comprobante comprobanteDomain = new Comprobante();
                comprobanteDomain.setServicio("Transferencia de Fondos");
                comprobanteDomain.setMontoPagado(monto);
                comprobanteDomain.setFecha(LocalDate.now());
                comprobanteDomain.setCodigoAutorizacion("TRX-" + System.currentTimeMillis());

                Comprobante comprobanteGuardado = comprobanteRepository.guardarComprobante(comprobanteDomain);
                comprobanteRepository.guardarComprobante(comprobanteDomain);
                return new ComprobanteDTO(
                    comprobanteGuardado.getId(), 
                    comprobanteDomain.getServicio(),
                    comprobanteDomain.getMontoPagado(), 
                    comprobanteDomain.getFecha(), 
                    comprobanteDomain.getCodigoAutorizacion()
                );
    }
    @Override
    @Transactional
    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO, String dni) {
        Long usuarioId = userRepository.findByDni(dni)
                .map(UsuarioDTO::id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con DNI: " + dni));
        ClienteEntity cliente = springDataClientRepository.findByIdUsuario_Id(usuarioId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuarioId: " + usuarioId));
        
        CuentaEntity nuevaCuentaEntity = cuentaPersistenceMapper.toEntity(cuentaDTO, cliente);
        
        CuentaEntity cuentaGuardada = springDataCuentaRepository.save(nuevaCuentaEntity);
    
        return cuentaPersistenceMapper.toDTO(cuentaGuardada);
    }
}
