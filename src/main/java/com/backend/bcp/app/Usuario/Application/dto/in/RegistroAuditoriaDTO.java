package com.backend.bcp.app.Usuario.Application.dto.in;

import java.time.LocalDateTime;

import com.backend.bcp.app.shared.Infraestructure.entity.enums.TipoRegistroAuditoria;

public record RegistroAuditoriaDTO(
    Long id,
    LocalDateTime fechaHora,
    TipoRegistroAuditoria tipoRegistro,
    String descripcion,
    Long usuarioIdRelacionado,
    Long entidadIdRelacionada,
    String detallesAdicionales
) {

}
