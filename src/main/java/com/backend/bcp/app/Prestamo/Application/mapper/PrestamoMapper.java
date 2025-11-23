package com.backend.bcp.app.Prestamo.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoResponseDTO;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;
import com.backend.bcp.app.shared.Domain.Usuario;
import com.backend.bcp.app.shared.Infraestructure.entity.UsuarioEntity;

@Component
public class PrestamoMapper {
public Prestamo toDomain(PrestamoEntity entity){
    if (entity ==null) {
        return null;
    }
    Usuario usuario = new Usuario();
    if (entity.getUsuario() !=null) {
        usuario.setId(entity.getUsuario().getId());
    }
    return new Prestamo(
        entity.getIdPrestamo(), 
        usuario, 
        entity.getMonto(), 
        entity.getInteres(), 
        entity.getPlazoMeses(),
        entity.getFechaInicio(), 
        entity.getEstado()
        );
    }
    public PrestamoEntity toEntity(Prestamo domain){
        if (domain == null) {
            return null;
        }
        PrestamoEntity entity = new PrestamoEntity();
        if (domain.getUsuario() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(domain.getUsuario().getId());
            entity.setUsuario(usuarioEntity);
        }
        entity.setMonto(domain.getMonto());
        entity.setInteres(domain.getInteres());
        entity.setPlazoMeses(domain.getPlazoMeses());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setEstado(domain.getEstado());
        return entity;
    }
    public PrestamoResponseDTO toDto(Prestamo domain) {
        if (domain == null) {
            return null;
        }
        return new PrestamoResponseDTO(
                domain.getId(),
                domain.getUsuario() != null ? domain.getUsuario().getId() : null,
                domain.getMonto(),
                domain.getPlazoMeses(),
                domain.getInteres(),
                domain.getEstado()
        );
    }
    public PrestamoPersistenceDTO toPersistenceDTO(Prestamo domain) {
        if (domain == null) return null;
        return new PrestamoPersistenceDTO(
            domain.getId(),
            domain.getUsuario() != null ? domain.getUsuario().getId() : null,
            domain.getMonto(),
            domain.getInteres(),
            domain.getPlazoMeses(),
            domain.getFechaInicio(),
            domain.getEstado()
        );
    }

    public Prestamo fromPersistenceDTO(PrestamoPersistenceDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        if (dto.usuarioId() != null) {
            usuario.setId(dto.usuarioId());
        }
        return new Prestamo(
            dto.id(),
            usuario,
            dto.monto(),
            dto.interes(),
            dto.plazoMeses(),
            dto.fechaInicio(),
            dto.estado()
        );
    }
    public PrestamoPersistenceDTO entityToPersistenceDTO(PrestamoEntity entity) {
        if (entity == null) return null;
        return new PrestamoPersistenceDTO(
            entity.getIdPrestamo(),
            entity.getUsuario() != null ? entity.getUsuario().getId() : null,
            entity.getMonto(),
            entity.getInteres(),
            entity.getPlazoMeses(),
            entity.getFechaInicio(),
            entity.getEstado()
        );
    }
    
    public PrestamoEntity persistenceDTOToEntity(PrestamoPersistenceDTO dto) {
         if (dto == null) return null;
        PrestamoEntity entity = new PrestamoEntity();
        
        if (dto.usuarioId() != null) {
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(dto.usuarioId());
            entity.setUsuario(usuarioEntity);
        }
        
        entity.setIdPrestamo(dto.id());
        entity.setMonto(dto.monto());
        entity.setInteres(dto.interes());
        entity.setPlazoMeses(dto.plazoMeses());
        entity.setFechaInicio(dto.fechaInicio());
        entity.setEstado(dto.estado());
        return entity;
    }
}
