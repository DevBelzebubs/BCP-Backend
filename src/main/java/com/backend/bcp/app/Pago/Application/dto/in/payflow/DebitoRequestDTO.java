package com.backend.bcp.app.Pago.Application.dto.in.payflow;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DebitoRequestDTO(
    @NotBlank(message = "El DNI del cliente es obligatorio")
    @Size(min = 8, max = 8)
    String dniCliente,

    @NotBlank(message = "El número de cuenta origen es obligatorio")
    String numeroCuentaOrigen,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal monto,

    @NotNull(message = "El ID del pago en BCP es obligatorio")
    Long idPagoBCP,
    
    @NotBlank(message = "El ID del servicio en Payflow es obligatorio")
    String idServicioPayflow
) {

}
