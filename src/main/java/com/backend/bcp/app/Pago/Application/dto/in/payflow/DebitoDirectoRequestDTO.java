package com.backend.bcp.app.Pago.Application.dto.in.payflow;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DebitoDirectoRequestDTO(
        @NotBlank(message = "El DNI del cliente es obligatorio") 
        @Size(min = 8, max = 8) String 
        dniCliente,
        @NotBlank(message = "El número de cuenta origen es obligatorio") 
        String numeroCuentaOrigen,
        @NotNull(message = "El monto es obligatorio") 
        @Positive(message = "El monto debe ser positivo") 
        BigDecimal monto,
        @NotBlank(message = "La descripción de la compra es obligatoria") 
        @Size(min = 5, message = "La descripción debe tener al menos 5 caracteres") 
        String descripcionCompra) {

}
