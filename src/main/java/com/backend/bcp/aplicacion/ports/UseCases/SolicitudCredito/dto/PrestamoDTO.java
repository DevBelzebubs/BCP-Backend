package com.backend.bcp.aplicacion.ports.UseCases.SolicitudCredito.dto;

public record PrestamoDTO(Long id, 
String nombre, String apellido, String dni, double monto, int plazo) {

}
