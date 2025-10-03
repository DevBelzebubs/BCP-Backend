package com.backend.bcp.app.Cuenta.Aplication.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Cuenta.Domain.Cuenta;

public interface CuentaRepository {
    List<Cuenta> buscarPorUsuarioId(Long usuarioId);
    Optional<Cuenta> buscarPorId(Long cuentaId);
}
