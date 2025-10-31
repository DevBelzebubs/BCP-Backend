package com.backend.bcp.app.Shared.Application.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Shared.Application.dto.in.LoadClientDataDTO;
import com.backend.bcp.app.Shared.Domain.ports.in.GestionClienteUseCase;

@Service
public class ClienteDashboardService implements GestionClienteUseCase {
    private final UserRepository userRepository;
    private final GestionCuentaUseCase gestionCuentaUseCase;
    private final RealizarPagoUseCase realizarPagoUseCase;
    

    public ClienteDashboardService(UserRepository userRepository, GestionCuentaUseCase gestionCuentaUseCase,
            RealizarPagoUseCase realizarPagoUseCase) {
        this.userRepository = userRepository;
        this.gestionCuentaUseCase = gestionCuentaUseCase;
        this.realizarPagoUseCase = realizarPagoUseCase;
    }

    @Override
    public LoadClientDataDTO cargarDatosDashboard(String dni) {
        UsuarioDTO user = userRepository.findByDni(dni).orElseThrow(() -> new RuntimeException("No se encontró usuario"));
        List<PagoPendienteDTO> pago = realizarPagoUseCase.listarPagosPendientes(dni);
        List<CuentaDTO> cuentas = gestionCuentaUseCase.listarCuentasPorUsuario(dni);
        List<DetalleCuentaDTO> cuentaDetalle = cuentas.stream().map(cuenta -> gestionCuentaUseCase.obtenerDetalleCuenta(cuenta.id())).collect(Collectors.toList());
        return new LoadClientDataDTO(user,cuentaDetalle,pago);
    }

}
