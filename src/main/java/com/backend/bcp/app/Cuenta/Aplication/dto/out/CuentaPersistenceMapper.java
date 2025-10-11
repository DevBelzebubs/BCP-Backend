package com.backend.bcp.app.Cuenta.Aplication.dto.out;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Aplication.dto.in.ClienteReferenceDTO;
import com.backend.bcp.app.Cuenta.Aplication.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;

@Component
public class CuentaPersistenceMapper {
    public CuentaDTO toPersistenceDTO(CuentaEntity entity) {
        if (entity == null) return null;

        ClienteReferenceDTO clienteRef = null;
        if (entity.getCliente() != null && entity.getCliente().getIdUsuario() != null) {
            clienteRef = new ClienteReferenceDTO(entity.getCliente().getIdUsuario().getId());
        }
        return new CuentaDTO(
            entity.getIdCuenta(),
            clienteRef,
            entity.getTipoCuenta(),
            entity.getEstadoCuenta(),
            entity.getNumeroCuenta(),
            entity.getSaldo()
        );
    }
}

