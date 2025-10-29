package com.backend.bcp.app.Usuario.Infraestructure.adapters.in;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Usuario.Application.dto.out.AuditoriaRolDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Admin.GestionAuditoriaUseCase;

@RestController
@RequestMapping("/api/admin/auditoria")
public class AdminAuditoriaController {
private final GestionAuditoriaUseCase gestionAuditoriaUseCase;

    public AdminAuditoriaController(GestionAuditoriaUseCase gestionAuditoriaUseCase) {
        this.gestionAuditoriaUseCase = gestionAuditoriaUseCase;
    }

    @GetMapping("/roles")
    public ResponseEntity<?> listarAuditoriasDeRoles() {
        try {
            List<AuditoriaRolDTO> auditorias = gestionAuditoriaUseCase.listarAuditoriasRol();
            return ResponseEntity.ok(auditorias);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al obtener auditorías de roles."));
        }
    }
}
