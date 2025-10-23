package com.backend.bcp.app.Servicio.Application.mapper;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Servicio.Application.dto.in.CrearServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Application.dto.out.ServicioResponseDTO;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;

@Service
public class ServicioMapper {
    public ServicioPersistenceDTO toPersistenceDTO(ServicioEntity entity){
        if (entity == null) return null;
        return new ServicioPersistenceDTO(
            entity.getIdServicio(), 
            entity.getNombre(), 
            entity.getDescripcion(), 
            entity.getRecibo());
    }
    public ServicioEntity toEntity(Servicio domain){
        if (domain == null) return null;
        ServicioEntity e = new ServicioEntity();
        e.setIdServicio(domain.getIdServicio());
        e.setNombre(domain.getNombre());
        e.setDescripcion(domain.getDescripcion());
        e.setRecibo(domain.getRecibo());
        return e;
    }
    public Servicio toDomain(ServicioPersistenceDTO dto) {
        if (dto == null) return null;
        return new Servicio(
            dto.id(),
            dto.nombre(),
            dto.descripcion(),
            dto.recibo()
        );
    }
    public Servicio toDomain(ServicioEntity entity) {
        if (entity == null) return null;
        return new Servicio(
            entity.getIdServicio(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getRecibo()
        );
    }
    public Servicio toDomain(CrearServicioDTO dto) {
        if (dto == null) return null;
        return new Servicio(
            null,
            dto.nombre(),
            dto.descripcion(),
            dto.recibo()
        );
    }
    public ServicioPersistenceDTO toPersistenceDTO(Servicio domain) {
        if (domain == null) return null;
        return new ServicioPersistenceDTO(
            domain.getIdServicio(),
            domain.getNombre(),
            domain.getDescripcion(),
            domain.getRecibo()
        );
    }
    public ServicioResponseDTO toResponseDTO(Servicio domain) {
        if (domain == null) return null;
        return new ServicioResponseDTO(
            domain.getIdServicio(),
            domain.getNombre(),
            domain.getDescripcion(),
            domain.getRecibo()
        );
    }
}
