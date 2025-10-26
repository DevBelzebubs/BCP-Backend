package com.backend.bcp.app.Cuenta.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoAppDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoDTO;
import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;

@Component
public class MovimientoAppPresentationMapper {
    public MovimientoDTO toPresentationDTO(MovimientoAppDTO appDTO) {
        if (appDTO == null) return null;
        return new MovimientoDTO(
            appDTO.id(),
            appDTO.tipo(),
            appDTO.monto(),
            appDTO.fecha()
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
