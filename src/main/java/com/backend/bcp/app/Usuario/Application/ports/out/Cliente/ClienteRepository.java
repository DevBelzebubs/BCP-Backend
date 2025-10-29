package com.backend.bcp.app.Usuario.Application.ports.out.Cliente;

import java.math.BigDecimal;

import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

public interface ClienteRepository {
    ClienteEntity findClienteByDni(String dni);
    Cuenta findCuentaByNumeroAndClienteId(String numeroCuenta, Long clienteId);
}
