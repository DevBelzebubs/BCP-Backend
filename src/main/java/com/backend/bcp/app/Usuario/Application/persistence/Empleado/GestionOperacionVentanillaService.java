package com.backend.bcp.app.Usuario.Application.persistence.Empleado;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Cuenta.Application.mapper.CuentaPersistenceMapper;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Pago.Infraestructure.repo.SpringDataPagoRepository;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Transaccion.Application.ports.out.TransaccionRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.DepositoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.PagoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.RetiroVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionOperacionVentanillaUseCase;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;

import jakarta.transaction.Transactional;

@Service
public class GestionOperacionVentanillaService implements GestionOperacionVentanillaUseCase {
    private final UserRepository userRepository;
    private final SpringDataClientRepository clientRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final SpringDataPagoRepository springDataPagoRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final CuentaPersistenceMapper cuentaMapper;
    
    public GestionOperacionVentanillaService(UserRepository userRepository, SpringDataClientRepository clientRepository,
            CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository,
            SpringDataPagoRepository springDataPagoRepository, ComprobanteRepository comprobanteRepository,
            CuentaPersistenceMapper cuentaMapper) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.springDataPagoRepository = springDataPagoRepository;
        this.comprobanteRepository = comprobanteRepository;
        this.cuentaMapper = cuentaMapper;
    }

    @Override
    @Transactional
    public ComprobanteDTO registrarDepositoVentanilla(DepositoVentanillaDTO request, Long empleadoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarDepositoVentanilla'");
    }

    @Override
    public ComprobanteDTO registrarRetiroVentanilla(RetiroVentanillaDTO request, Long empleadoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarRetiroVentanilla'");
    }

    @Override
    public ComprobanteDTO registrarPagoVentanilla(PagoVentanillaDTO request, Long empleadoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarPagoVentanilla'");
    }

}
