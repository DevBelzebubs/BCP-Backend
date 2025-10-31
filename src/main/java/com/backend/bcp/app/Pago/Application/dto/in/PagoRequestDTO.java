package com.backend.bcp.app.Pago.Application.dto.in;


import jakarta.validation.constraints.NotNull;

public record PagoRequestDTO(
    @NotNull(message = "El ID de la cuenta no puede ser nulo")    
    Long cuentaId, 
    @NotNull(message = "El ID del pago (factura) no puede ser nulo")
    Long pagoId) {
}
