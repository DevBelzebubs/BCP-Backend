package com.backend.bcp.app.Reclamo.Application.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CrearReclamoRequestDTO {
    @NotBlank(message = "El DNI del cliente es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
    private String dniCliente;
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String descripcion;
    public CrearReclamoRequestDTO() {
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }
    
}
