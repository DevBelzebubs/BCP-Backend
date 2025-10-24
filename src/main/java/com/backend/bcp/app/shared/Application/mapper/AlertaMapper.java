package com.backend.bcp.app.Shared.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Shared.Application.dto.out.AlertaPersistenceDTO;
import com.backend.bcp.app.Shared.Infraestructure.entity.AlertaEntity;

@Component
public class AlertaMapper {
    public AlertaEntity toEntity(AlertaPersistenceDTO dto) {
        AlertaEntity entity = new AlertaEntity();
        entity.setIdAlerta(dto.id()); 
        entity.setFechaHoraCreacion(dto.fechaHoraCreacion());
        entity.setTipoAlerta(dto.tipoAlerta());
        entity.setMensaje(dto.mensaje());
        entity.setEstado(dto.estado());
        entity.setUsuarioNotificadoId(dto.usuarioNotificadoId());
        return entity;
    }

    public AlertaPersistenceDTO toDTO(AlertaEntity entity) {
        return new AlertaPersistenceDTO(
                entity.getIdAlerta(),
                entity.getFechaHoraCreacion(),
                entity.getTipoAlerta(),
                entity.getMensaje(),
                entity.getEstado(),
                entity.getUsuarioNotificadoId()
        );
    }
}
