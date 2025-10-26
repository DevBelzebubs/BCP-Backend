package com.backend.bcp.app.Transaccion.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoDTO;
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

        CuentaEntity cuentaEntity = new CuentaEntity();
        if (domain.getCuenta() != null) {
            cuentaEntity.setIdCuenta(domain.getCuenta().getId());
        }

        TransaccionEntity entity = new TransaccionEntity();
        entity.setCuenta(cuentaEntity);
        entity.setTipo(domain.getTipo());
        entity.setMonto(domain.getMonto());
        entity.setFecha(domain.getFecha());
        return entity;
    }
    public MovimientoAppDTO mapEntityToAppDTO(TransaccionEntity entity) {
         if (entity == null) return null;
         Long cuentaId = (entity.getCuenta() != null) ? entity.getCuenta().getIdCuenta() : null;
         return new MovimientoAppDTO(
             entity.getIdTransaccion(),
             cuentaId,
             entity.getTipo(),
             entity.getMonto(),
             entity.getFecha()
         );
    }
    public TransaccionEntity mapAppDTOToEntity(MovimientoAppDTO dto) {
        if (dto == null) return null;

        CuentaEntity cuentaRef = new CuentaEntity();
        if (dto.cuentaId() != null) {
            cuentaRef.setIdCuenta(dto.cuentaId());
        } else {
             throw new IllegalArgumentException("El ID de la cuenta es necesario para guardar la transacción.");
        }

        TransaccionEntity entity = new TransaccionEntity();
        entity.setCuenta(cuentaRef);
        entity.setTipo(dto.tipo());
        entity.setMonto(dto.monto());
        entity.setFecha(dto.fecha());
        return entity;
    }
    public MovimientoDTO mapAppDTOToMovimientoDTO(MovimientoAppDTO appDTO) {
     if (appDTO == null) return null;
     return new MovimientoDTO(
         appDTO.id(),
         appDTO.tipo(),
         appDTO.monto(),
         appDTO.fecha()
     );
    }
}