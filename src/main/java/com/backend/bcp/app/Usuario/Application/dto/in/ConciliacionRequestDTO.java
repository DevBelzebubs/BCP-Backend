package com.backend.bcp.app.Usuario.Application.dto.in;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ConciliacionRequestDTO(
    @NotNull(message = "La fecha de conciliación es obligatoria")
    LocalDate fechaConciliacion,

    @NotEmpty(message = "La lista de operaciones no puede estar vacía")
    List<OperacionInterbancariaDTO> operaciones
) {

}
