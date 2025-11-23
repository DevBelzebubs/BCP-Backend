package com.backend.bcp.app.shared.Application.dto.out;

import com.backend.bcp.app.shared.Infraestructure.entity.enums.TipoRol;

public record EmpleadoRolDTO(
    Long empleadoId,
    Long usuarioId,
    String nombreUsuario,
    String correoUsuario,
    TipoRol rolActual
) {

}
