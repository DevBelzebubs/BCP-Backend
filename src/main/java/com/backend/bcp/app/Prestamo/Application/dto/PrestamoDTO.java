package com.backend.bcp.app.Prestamo.Application.dto;

public record PrestamoDTO(Long id, 
String nombre, String apellido, String dni, double monto, int plazo) {

}
