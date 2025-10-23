package com.backend.bcp.app.Pago.Application.dto.in;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record PagoUpdateMontoRequestDTO(
    @NotNull(message = "El nuevo monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    BigDecimal nuevoMonto,
    @NotNull(message = "El nombre del Servicio no debe ser nulo")
    String nombreServicio
    ) {

}
