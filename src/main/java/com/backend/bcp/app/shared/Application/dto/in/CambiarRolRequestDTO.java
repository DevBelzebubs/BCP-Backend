package com.backend.bcp.app.Shared.Application.dto.in;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CambiarRolRequestDTO(
    @NotBlank(message = "El nuevo rol es obligatorio")
    @Pattern(regexp = "ASESOR|BACKOFFICE", message = "El rol debe ser ASESOR o BACKOFFICE")
    TipoRol nuevoRol
) {

}