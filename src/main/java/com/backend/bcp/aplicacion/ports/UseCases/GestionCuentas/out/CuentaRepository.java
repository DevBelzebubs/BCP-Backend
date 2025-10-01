package com.backend.bcp.aplicacion.ports.UseCases.GestionCuentas.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.dominio.Cuenta;

public interface CuentaRepository {
    List<Cuenta> buscarPorUsuarioId(Long usuarioId);
    Optional<Cuenta> buscarPorId(Long cuentaId);
}
