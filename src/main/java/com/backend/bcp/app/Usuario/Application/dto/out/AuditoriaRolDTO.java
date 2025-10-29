package com.backend.bcp.app.Usuario.Application.dto.out;

import java.time.LocalDateTime;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;

public record AuditoriaRolDTO(
    Long id,
    LocalDateTime fechaHoraCambio,
    Long adminId,
    Long empleadoId,
    TipoRol rolAnterior,
    TipoRol rolNuevo) {
}