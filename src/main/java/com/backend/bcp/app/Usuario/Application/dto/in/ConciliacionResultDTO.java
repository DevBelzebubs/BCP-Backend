package com.backend.bcp.app.Usuario.Application.dto.in;

public record ConciliacionResultDTO(
    String estado,
    String mensaje,
    Long alertaId
) {

}
