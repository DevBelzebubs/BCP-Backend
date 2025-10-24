package com.backend.bcp.app.Shared.Application.dto.out;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;

public record EmpleadoRolDTO(
    Long empleadoId,
    Long usuarioId,
    String nombreUsuario,
    String correoUsuario,
    TipoRol rolActual
) {

}
