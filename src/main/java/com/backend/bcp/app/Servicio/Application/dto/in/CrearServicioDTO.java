package com.backend.bcp.app.Servicio.Application.dto.in;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearServicioDTO(
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,
    
    String descripcion,
    
    @NotNull(message = "El recibo (monto) no puede ser nulo")
    @Positive(message = "El recibo debe ser un monto positivo")
    BigDecimal recibo
    ) {

}
