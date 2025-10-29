package com.backend.bcp.app.Usuario.Infraestructure.adapters.out;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Cuenta.Application.mapper.CuentaPersistenceMapper;
import com.backend.bcp.app.Cuenta.Application.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Application.ports.out.Cliente.ClienteRepository;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;

@Service
public class ClienteRepositoryImpl implements ClienteRepository{
    private final UserRepository userRepository;
    private final SpringDataClientRepository clientRepository;
    private final CuentaPersistenceMapper cuentaMapper;
    private final CuentaRepository cuentaRepository;
    public ClienteRepositoryImpl(UserRepository userRepository, SpringDataClientRepository clientRepository, CuentaPersistenceMapper cuentaMapper, CuentaRepository cuentaRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.cuentaMapper = cuentaMapper;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public ClienteEntity findClienteByDni(String dni) {
       UsuarioDTO usuarioDto = userRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no identificado con DNI: " + dni));
        return clientRepository.findByIdUsuario_Id(usuarioDto.id())
                .orElseThrow(() -> new RuntimeException("Registro interno de cliente no encontrado para DNI: " + dni));
    }

    @Override
    public Cuenta findCuentaByNumeroAndClienteId(String numeroCuenta, Long clienteId) {
        List<Cuenta> cuentasCliente = cuentaRepository.obtenerCuentasPorUsuario(clienteId)
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

}
