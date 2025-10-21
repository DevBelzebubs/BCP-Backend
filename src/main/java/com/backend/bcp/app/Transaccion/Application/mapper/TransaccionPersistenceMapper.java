package com.backend.bcp.app.Transaccion.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;

@Component
public class TransaccionPersistenceMapper {
    public MovimientoPersistenceDTO toPersistenceDTO(TransaccionEntity entity) {
        if (entity == null) return null;

        Long cuentaId = (entity.getCuenta() != null) ? entity.getCuenta().getIdCuenta() : null;
        
        return new MovimientoPersistenceDTO(
            entity.getIdTransaccion(),
            cuentaId,
            entity.getTipo(),
            entity.getMonto(),
            entity.getFecha()
        );
    }
    
    public TransaccionEntity toEntity(Transaccion domain) {
        if (domain == null) return null;

        // Se usa el ID de la cuenta de dominio para la clave foránea (FK)
        CuentaEntity cuentaEntity = new CuentaEntity();
        if (domain.getCuenta() != null) {
            cuentaEntity.setIdCuenta(domain.getCuenta().getId());
        }

        TransaccionEntity entity = new TransaccionEntity();
        // ID de entidad se autogenera
        entity.setCuenta(cuentaEntity);
        entity.setTipo(domain.getTipo());
        entity.setMonto(domain.getMonto());
        entity.setFecha(domain.getFecha());
        return entity;
    }
}