package com.backend.bcp.app.Usuario.Application.dto.in.Empleado;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PagoVentanillaDTO extends OperacionVentanillaBaseDTO {
    @NotNull(message = "El ID del pago (servicio/préstamo) es obligatorio")
    @Positive(message = "El ID del pago debe ser positivo")
    private Long pagoId;

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }
    
}
