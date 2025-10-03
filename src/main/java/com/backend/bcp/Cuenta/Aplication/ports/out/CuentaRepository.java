package com.backend.bcp.Cuenta.Aplication.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.Cuenta.Domain.Cuenta;

public interface CuentaRepository {
    List<Cuenta> buscarPorUsuarioId(Long usuarioId);
    Optional<Cuenta> buscarPorId(Long cuentaId);
}
