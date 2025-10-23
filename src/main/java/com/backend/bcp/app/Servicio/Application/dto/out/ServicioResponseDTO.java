package com.backend.bcp.app.Servicio.Application.dto.out;

import java.math.BigDecimal;

public record ServicioResponseDTO(
    Long idServicio,
    String nombre,
    String descripcion,
    BigDecimal recibo
) {

}
