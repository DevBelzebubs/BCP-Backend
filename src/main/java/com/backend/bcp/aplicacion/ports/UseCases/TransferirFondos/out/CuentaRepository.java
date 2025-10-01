package com.backend.bcp.aplicacion.ports.UseCases.TransferirFondos.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.dominio.Cuenta;

public interface CuentaRepository {
    List<Cuenta> obtenerCuentasPorUsuario(Long usuarioId);
    Optional<Cuenta> obtenerPorId(Long cuentaId);
    void actualizar(Cuenta cuenta);
}
