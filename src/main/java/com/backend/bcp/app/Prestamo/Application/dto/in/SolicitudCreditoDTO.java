package com.backend.bcp.app.Prestamo.Application.dto.in;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SolicitudCreditoDTO(
    @NotNull(message = "El ID de usuario no puede ser nulo")
    Long usuarioId,
    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 1, message = "El monto debe ser mayor a cero")
    BigDecimal monto,
    @NotNull(message = "El plazo en meses no puede ser nulo")
    @Min(value = 1, message = "El plazo debe ser de al menos 1 mes")
    Integer plazoMeses
    ) {
}
