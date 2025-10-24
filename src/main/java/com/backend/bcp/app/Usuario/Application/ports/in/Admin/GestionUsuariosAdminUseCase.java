package com.backend.bcp.app.Usuario.Application.ports.in.Admin;

import java.util.List;

import com.backend.bcp.app.Shared.Application.dto.in.CambiarRolRequestDTO;
import com.backend.bcp.app.Shared.Application.dto.out.EmpleadoRolDTO;

public interface GestionUsuariosAdminUseCase {
    List<EmpleadoRolDTO> listarEmpleadosConRoles();

    EmpleadoRolDTO cambiarRolEmpleado(Long empleadoId, CambiarRolRequestDTO request, Long adminUserId);
}
