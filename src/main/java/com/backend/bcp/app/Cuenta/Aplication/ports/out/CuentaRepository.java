package com.backend.bcp.app.Cuenta.Aplication.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Cuenta.Aplication.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;

public interface CuentaRepository {
    List<CuentaDTO> obtenerCuentasPorUsuario(Long usuarioId);
    Optional<CuentaDTO> obtenerPorId(Long cuentaId);
    void actualizar(Cuenta cuenta);
}