package com.backend.bcp.app.Cuenta.Application.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Cuenta.Application.dto.out.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;

public interface CuentaRepository {
    List<CuentaPersistenceDTO> obtenerCuentasPorUsuario(Long usuarioId);
    Optional<CuentaPersistenceDTO> obtenerPorId(Long cuentaId);
    void actualizar(Cuenta cuenta);
}