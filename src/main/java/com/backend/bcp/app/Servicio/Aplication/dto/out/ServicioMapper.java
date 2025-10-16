package com.backend.bcp.app.Servicio.Aplication.dto.out;

import com.backend.bcp.app.Servicio.Aplication.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;

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
}
