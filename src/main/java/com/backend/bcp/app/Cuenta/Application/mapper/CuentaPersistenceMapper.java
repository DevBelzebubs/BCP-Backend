package com.backend.bcp.app.Cuenta.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Application.dto.in.ClienteReferenceDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.out.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoDTO;
import com.backend.bcp.app.Transaccion.Application.dto.out.MovimientoPersistenceDTO;
import com.backend.bcp.app.Usuario.Domain.Cliente;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

@Component
public class CuentaPersistenceMapper {
    public CuentaPersistenceDTO toPersistenceDTO(CuentaEntity entity) {
        if (entity == null) return null;

        ClienteReferenceDTO clienteRef = null;
        if (entity.getCliente() != null) {
            clienteRef = new ClienteReferenceDTO(entity.getCliente().getIdCliente());
        }
        return new CuentaPersistenceDTO(
            entity.getIdCuenta(),
            clienteRef,
            entity.getTipoCuenta(),
            entity.getEstadoCuenta(),
            entity.getNumeroCuenta(),
            entity.getSaldo()
        );
    }
    public CuentaEntity toEntity(CuentaDTO cuentaDTO, ClienteEntity cliente){
        if (cuentaDTO == null) return null;
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo para crear una cuenta.");
        
        CuentaEntity entity = new CuentaEntity();
        entity.setCliente(cliente);
        entity.setTipoCuenta(cuentaDTO.tipo());
        entity.setEstadoCuenta("ACTIVO");
        entity.setNumeroCuenta(cuentaDTO.numeroCuenta());
        entity.setSaldo(cuentaDTO.saldo());
        return entity;
    }
    public CuentaDTO toDTO(CuentaEntity entity) {
        if (entity == null) return null;
        return new CuentaDTO(
            entity.getIdCuenta(),
            entity.getTipoCuenta(),
            entity.getNumeroCuenta(),
            entity.getSaldo()
        );
    }
    public Cuenta toDomain(CuentaPersistenceDTO dto){
        if (dto == null) throw new RuntimeException("Cuenta no encontrada.");

        Cliente cliente = new Cliente();
        cliente.setId(dto.cliente().id());
        return new Cuenta(
            dto.id(), cliente, dto.tipo(), dto.estadoCuenta(), dto.numeroCuenta(), dto.saldo()
        );
    }
    
    public CuentaDTO toPresentationDTO(CuentaPersistenceDTO dto) {
        return new CuentaDTO(dto.id(), dto.tipo(), dto.numeroCuenta(), dto.saldo());
    }

    public MovimientoDTO toMovimientoDTO(MovimientoPersistenceDTO dto) {
        return new MovimientoDTO(dto.id(), dto.tipo(), dto.monto(), dto.fecha());
    }
}

