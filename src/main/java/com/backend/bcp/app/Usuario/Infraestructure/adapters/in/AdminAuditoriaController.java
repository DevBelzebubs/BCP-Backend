package com.backend.bcp.app.Usuario.Infraestructure.adapters.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Usuario.Application.dto.out.AuditoriaRolDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Admin.GestionAuditoriaUseCase;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

@RestController
@RequestMapping("/api/admin/auditoria")
public class AdminAuditoriaController {
private final GestionAuditoriaUseCase gestionAuditoriaUseCase;

    public AdminAuditoriaController(GestionAuditoriaUseCase gestionAuditoriaUseCase) {
        this.gestionAuditoriaUseCase = gestionAuditoriaUseCase;
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<AuditoriaRolDTO>>> listarAuditoriasDeRoles() {
        try {
            List<AuditoriaRolDTO> auditorias = gestionAuditoriaUseCase.listarAuditoriasRol();
            return ResponseEntity.ok(ApiResponse.success("Listado de auditorías obtenido", auditorias));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error al obtener auditorías de roles: " + e.getMessage(), null));
        }
    }
}
