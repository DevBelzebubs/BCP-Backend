package com.backend.bcp.app.Comprobante.Infraestructure.adapters.out;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Comprobante.Infraestructure.entity.ComprobanteEntity;

@Component
public class ComprobanteMapper {
    public ComprobanteEntity toEntity(Comprobante domain){
        if (domain == null) return null;
        ComprobanteEntity entity = new ComprobanteEntity();
        entity.setIdComprobante(domain.getId());
        entity.setServicio(domain.getServicio());
        entity.setMontoPagado(domain.getMontoPagado());
        entity.setFecha(domain.getFecha());
        entity.setCodigoAutorizacion(domain.getCodigoAutorizacion());
        return entity;
    }
    public Comprobante toDomain(ComprobanteEntity entity) {
        if (entity == null) return null;
        Comprobante domain = new Comprobante();
        domain.setId(entity.getIdComprobante());
        domain.setServicio(entity.getServicio());
        domain.setMontoPagado(entity.getMontoPagado());
        domain.setFecha(entity.getFecha());
        domain.setCodigoAutorizacion(entity.getCodigoAutorizacion());
        return domain;
    }
}
