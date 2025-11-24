package com.backend.bcp.app.Usuario.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Usuario.Application.dto.in.RegistroAuditoriaDTO;
import com.backend.bcp.app.Usuario.Application.dto.out.AuditoriaRolDTO;
import com.backend.bcp.app.shared.Infraestructure.entity.AuditoriaRolEntity;
import com.backend.bcp.app.shared.Infraestructure.entity.RegistroAuditoriaFinanciera;

@Component
public class AuditoriaPersistence {

    public AuditoriaRolDTO mapToDTO(AuditoriaRolEntity entity) {
        return new AuditoriaRolDTO(
                entity.getId(),
                entity.getFechaHoraCambio(),
                entity.getAdminId(),
                entity.getEmpleadoId(),
                entity.getRolAnterior(),
                entity.getRolNuevo());
    }

    public AuditoriaRolDTO mapToRolDTO(AuditoriaRolEntity entity) {
        return new AuditoriaRolDTO(
                entity.getId(),
                entity.getFechaHoraCambio(),
                entity.getAdminId(),
                entity.getEmpleadoId(),
                entity.getRolAnterior(),
                entity.getRolNuevo());
    }

    public RegistroAuditoriaDTO mapToFinancieraDTO(RegistroAuditoriaFinanciera entity) {
        return new RegistroAuditoriaDTO(
                entity.getId(),
                entity.getFechaHora(),
                entity.getTipoRegistro(),
                entity.getDescripcion(),
                entity.getUsuarioIdRelacionado(),
                entity.getEntidadIdRelacionada(),
                entity.getDetallesAdicionales());
    }
}
