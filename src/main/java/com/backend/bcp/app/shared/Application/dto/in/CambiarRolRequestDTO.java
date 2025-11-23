package com.backend.bcp.app.shared.Application.dto.in;

import com.backend.bcp.app.shared.Infraestructure.entity.enums.TipoRol;

import jakarta.validation.constraints.NotNull;

public record CambiarRolRequestDTO(
    @NotNull(message = "El nuevo rol es obligatorio")
    TipoRol nuevoRol
) {

}