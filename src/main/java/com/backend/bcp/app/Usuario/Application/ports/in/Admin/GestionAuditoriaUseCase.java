package com.backend.bcp.app.Usuario.Application.ports.in.Admin;

import java.util.List;

import com.backend.bcp.app.Usuario.Application.dto.in.RegistroAuditoriaDTO;
import com.backend.bcp.app.Usuario.Application.dto.out.AuditoriaRolDTO;

public interface GestionAuditoriaUseCase {
    List<AuditoriaRolDTO> listarAuditoriasRol();
    List<RegistroAuditoriaDTO> listarAuditoriasFinancieras();
}
