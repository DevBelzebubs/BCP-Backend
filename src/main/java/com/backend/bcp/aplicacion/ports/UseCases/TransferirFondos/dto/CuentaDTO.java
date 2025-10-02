package com.backend.bcp.aplicacion.ports.UseCases.TransferirFondos.dto;

public record CuentaDTO(
    Long id,
    String tipo,
    String numeroCuenta
) {

}
