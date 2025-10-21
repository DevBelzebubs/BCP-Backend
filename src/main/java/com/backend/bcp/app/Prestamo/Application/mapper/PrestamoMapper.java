package com.backend.bcp.app.Prestamo.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Prestamo.Application.dto.in.PrestamoDTO;
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
        entity.getMontoInteres(), 
        entity.getTasaInteres(), 
        entity.getnCuotas(),
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
        entity.setMontoInteres(domain.getMonto());
        entity.setTasaInteres(domain.getInteres());
        entity.setnCuotas(domain.getPlazoMeses());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setEstado(domain.getEstado());
        return entity;
    }
    public PrestamoDTO toDto(Prestamo domain) {
        if (domain == null) {
            return null;
        }
        return new PrestamoDTO(
                domain.getId(),
                domain.getUsuario() != null ? domain.getUsuario().getId() : null,
                domain.getMonto(),
                domain.getPlazoMeses(),
                domain.getInteres(),
                domain.getEstado()
        );
    }
}
