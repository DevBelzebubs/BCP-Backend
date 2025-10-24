package com.backend.bcp.app.Usuario.Application.dto.in;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperacionInterbancariaDTO(
    String idOperacionExterna,
    LocalDate fecha,
    BigDecimal monto,
    String tipo,
    String descripcion
) {}