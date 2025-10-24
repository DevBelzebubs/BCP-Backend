package com.backend.bcp.app.Usuario.Application.ports.out.Admin;

import java.time.LocalDateTime;

import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;

public interface AuditoriaRolPort {
    void guardarAuditoria(Long adminId, Long empleadoId, TipoRol rolAnterior, TipoRol rolNuevo, LocalDateTime fechaHora);   
}