package com.backend.bcp.app.Usuario.Application.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MarcarSospechosoRequestDTO(
    @NotNull(message = "El ID de la operación es obligatorio")
    Long operacionId,

    @NotBlank(message = "El tipo de operación (RECLAMO/TRANSACCION) es obligatorio")
    String tipoOperacion,

    @NotBlank(message = "Se requiere una justificación")
    String justificacion
) {

}
