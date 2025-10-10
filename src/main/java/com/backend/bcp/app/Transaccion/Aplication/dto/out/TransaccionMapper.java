package com.backend.bcp.app.Transaccion.Aplication.dto.out;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;

@Component
public class TransaccionMapper {
    public TransaccionEntity toEntity(Transaccion transaccion){
        if(transaccion == null) return null;
        CuentaEntity cuentaEntity = new CuentaEntity();
        cuentaEntity.setIdCuenta(transaccion.getCuenta().getId());
        TransaccionEntity entity = new TransaccionEntity();
        entity.setCuenta(cuentaEntity);
        entity.setTipo(transaccion.getTipo());
        entity.setMonto(transaccion.getMonto());
        entity.setFecha(transaccion.getFecha());
        return entity;
    }
}