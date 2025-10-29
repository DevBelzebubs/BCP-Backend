package com.backend.bcp.app.Usuario.Application.dto.in.Empleado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OperacionVentanillaBaseDTO {
    @NotBlank(message = "El DNI del cliente es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String clienteDni;

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;

    public String getClienteDni() {
        return clienteDni;
    }

    public void setClienteDni(String clienteDni) {
        this.clienteDni = clienteDni;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
}
