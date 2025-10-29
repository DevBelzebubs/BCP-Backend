package com.backend.bcp.app.Usuario.Application.persistence.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.DepositoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.PagoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.RetiroVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionOperacionVentanillaUseCase;
import com.backend.bcp.app.Usuario.Application.ports.out.Cliente.ClienteRepository;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

import jakarta.transaction.Transactional;

@Service
public class GestionOperacionVentanillaService implements GestionOperacionVentanillaUseCase {
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final SpringDataPagoRepository springDataPagoRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final ClienteRepository clienteRepository;

    public GestionOperacionVentanillaService(CuentaRepository cuentaRepository,TransaccionRepository transaccionRepository,
            SpringDataPagoRepository springDataPagoRepository, ComprobanteRepository comprobanteRepository,
            ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.springDataPagoRepository = springDataPagoRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public ComprobanteDTO registrarDepositoVentanilla(DepositoVentanillaDTO request, Long empleadoId) {
        ClienteEntity cliente = clienteRepository.findClienteByDni(request.getClienteDni());
        Cuenta cuenta = clienteRepository.findCuentaByNumeroAndClienteId(request.getNumeroCuenta(), cliente.getIdCliente());
        BigDecimal monto = request.getMonto();
        cuenta.depositar(monto);
        cuentaRepository.actualizar(cuenta);

        MovimientoAppDTO transaccionDTO = new MovimientoAppDTO(null, cuenta.getId(), "DEPOSITO", monto, LocalDateTime.now());
        transaccionRepository.guardarTransaccion(transaccionDTO);

        return comprobanteRepository.generarComprobante("Depósito en Ventanilla", monto, "DEP-V-" + System.currentTimeMillis());
    }

    @Override
    @Transactional
    public ComprobanteDTO registrarRetiroVentanilla(RetiroVentanillaDTO request, Long empleadoId) {
        ClienteEntity cliente = clienteRepository.findClienteByDni(request.getClienteDni());
        Cuenta cuenta = clienteRepository.findCuentaByNumeroAndClienteId(request.getNumeroCuenta(), cliente.getIdCliente());
        BigDecimal monto = request.getMonto();
        cuenta.retirar(monto);
        cuentaRepository.actualizar(cuenta);

        MovimientoAppDTO transaccionDTO = new MovimientoAppDTO(null, cuenta.getId(), "RETIRO", monto, LocalDateTime.now());
        transaccionRepository.guardarTransaccion(transaccionDTO);

        return comprobanteRepository.generarComprobante("Retiro en Ventanilla", monto, "RET-V-" + System.currentTimeMillis());
    }

    @Override
    public ComprobanteDTO registrarPagoVentanilla(PagoVentanillaDTO request, Long empleadoId) {
        ClienteEntity cliente = clienteRepository.findClienteByDni(request.getClienteDni());
        Cuenta cuentaDebito = clienteRepository.findCuentaByNumeroAndClienteId(request.getNumeroCuenta(), cliente.getIdCliente());
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
        } // Añadir lógica para PagoPrestamoEntity si existe

        return comprobanteRepository.generarComprobante(detalleComprobante, montoPago, "PAG-V-" + pagoEntity.getIdPago());
    }

}
