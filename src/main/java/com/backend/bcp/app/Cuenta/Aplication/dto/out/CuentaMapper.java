package com.backend.bcp.app.Cuenta.Aplication.dto.out;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;
import com.backend.bcp.app.Usuario.Domain.Cliente;

public class CuentaMapper {
    public Cuenta toDomain(CuentaEntity cuentaEntity){
        if (cuentaEntity == null) return null;
        Cliente cliente = new Cliente(); 
        cliente.setId(cuentaEntity.getCliente().getIdCliente());
        return new Cuenta(cuentaEntity.getIdCuenta(), cliente, cuentaEntity.getTipoCuenta(), cuentaEntity.getEstadoCuenta(), cuentaEntity.getNumeroCuenta(), cuentaEntity.getSaldo());
    }
    public Transaccion toDomain(TransaccionEntity transaccionEntity, Cuenta cuenta){
        if (transaccionEntity == null);
            return new Transaccion(transaccionEntity.getIdTransaccion(), cuenta, transaccionEntity.getTipo() , transaccionEntity.getMonto(), transaccionEntity.getFecha());
        }
    public List<Transaccion> toDomain(List<TransaccionEntity> entities, Cuenta cuenta){
        return entities.stream()
        .map(entity -> toDomain(entity,cuenta)).collect(Collectors.toList());
    }
}

