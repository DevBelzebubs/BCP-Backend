package com.backend.bcp.app.Servicio.Aplication.dto.in;

import java.math.BigDecimal;

public record ServicioPersistenceDTO(Long id,
    String nombre,
    String descripcion,
    BigDecimal recibo) {
}