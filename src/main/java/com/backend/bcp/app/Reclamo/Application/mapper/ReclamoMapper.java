package com.backend.bcp.app.Reclamo.Application.mapper;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Reclamo.Application.dto.in.CrearReclamoRequestDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoPersistenceDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoResponseDTO;
import com.backend.bcp.app.Reclamo.Domain.Reclamo;
import com.backend.bcp.app.Reclamo.Domain.Reclamo.EstadoReclamo;
import com.backend.bcp.app.Reclamo.Infraestructure.entity.ReclamoEntity;
import com.backend.bcp.app.Usuario.Domain.Cliente;
import com.backend.bcp.app.Usuario.Domain.Empleado;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;

@Component
public class ReclamoMapper {
    public ReclamoPersistenceDTO domainToPersistenceDto(Reclamo domain) {
        if (domain == null) return null;
        return new ReclamoPersistenceDTO(
            domain.getId(),
            domain.getCliente() != null ? domain.getCliente().getId() : null,
            domain.getEmpleado() != null ? domain.getEmpleado().getId() : null,
            domain.getFechaCreacion(),
            domain.getDescripcion(),
            domain.getEstado(),
            domain.getRespuesta(),
            null
        );
    }
    public ReclamoResponseDTO domainToResponseDto(Reclamo domain) {
        if (domain == null) return null;

        ReclamoResponseDTO dto = new ReclamoResponseDTO();
        dto.setIdReclamo(domain.getId());
        dto.setClienteId(domain.getCliente() != null ? domain.getCliente().getId() : null);
        dto.setEmpleadoId(domain.getEmpleado() != null ? domain.getEmpleado().getId() : null);
        dto.setFechaCreacion(domain.getFechaCreacion());
        dto.setDescripcion(domain.getDescripcion());
        dto.setEstadoReclamo(domain.getEstado());
        dto.setRespuesta(domain.getRespuesta());
        return dto;
    }

    public Reclamo persistenceDtoToDomain(ReclamoResponseDTO dto) {
        if (dto == null) return null;

        Cliente cliente = null;
        if (dto.getClienteId() != null) {
            cliente = new Cliente();
            cliente.setId(dto.getClienteId());
        }

        Empleado empleado = null;
        if (dto.getEmpleadoId() != null) {
            empleado = new Empleado();
            empleado.setId(dto.getEmpleadoId());
        }

        Reclamo domain = new Reclamo(
            dto.getIdReclamo(),
            cliente,
            empleado,
            dto.getFechaCreacion(),
            dto.getDescripcion(),
            dto.getEstadoReclamo(),
            dto.getRespuesta(),
            dto.getNumeroSeguimiento()
        );
        return domain;
    }
    public ReclamoPersistenceDTO entityToPersistenceDto(ReclamoEntity entity) {
        if (entity == null) return null;
        return new ReclamoPersistenceDTO(
            entity.getIdReclamo(),
            entity.getCliente() != null ? entity.getCliente().getIdCliente() : null,
            entity.getEmpleado() != null ? entity.getEmpleado().getIdEmpleado() : null,
            entity.getFechaCreacion(),
            entity.getDescripcion(),
            entity.getEstadoReclamo(),
            entity.getRespuesta(),
            entity.getNumeroSeguimiento()
        );
    }

    public ReclamoEntity persistenceDtoToEntity(ReclamoResponseDTO dto) {
        if (dto == null) return null;
        ReclamoEntity entity = new ReclamoEntity();

        entity.setIdReclamo(dto.getIdReclamo());

        if (dto.getClienteId() != null) {
            ClienteEntity clienteRef = new ClienteEntity();
            clienteRef.setIdCliente(dto.getClienteId());
            entity.setCliente(clienteRef);
        }
        if (dto.getEmpleadoId() != null) {
            EmpleadoEntity empleadoRef = new EmpleadoEntity();
            empleadoRef.setIdEmpleado(dto.getEmpleadoId());
            entity.setEmpleado(empleadoRef);
        }
        entity.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDate.now());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstadoReclamo(dto.getEstadoReclamo() != null ? dto.getEstadoReclamo() : EstadoReclamo.PENDIENTE);
        entity.setRespuesta(dto.getRespuesta());
        entity.setNumeroSeguimiento(dto.getNumeroSeguimiento());

        return entity;
    }
    public Reclamo requestDtoToDomain(CrearReclamoRequestDTO dto) {
        if (dto == null) return null;
        Cliente cliente = new Cliente();
        cliente.setId(dto.getClienteId());
        return new Reclamo(null, cliente, null, LocalDate.now(), dto.getDescripcion(), EstadoReclamo.PENDIENTE, null,null);
    }

    public Reclamo persistenceDtoToDomain(ReclamoPersistenceDTO dto) {
        if (dto == null) return null;
        Cliente cliente = null;
        if (dto.clienteId() != null) {
            cliente = new Cliente();
            cliente.setId(dto.clienteId());
        }
        Empleado empleado = null;
        if (dto.empleadoId() != null) {
            empleado = new Empleado();
            empleado.setId(dto.empleadoId());
        }
        return new Reclamo(
            dto.idReclamo(), cliente, empleado, dto.fechaCreacion(),
            dto.descripcion(), dto.estadoReclamo(), dto.respuesta(), dto.numeroSeguimiento()
        );
    }

    public ReclamoEntity persistenceDtoToEntity(ReclamoPersistenceDTO dto) {
        if (dto == null) return null;
        ReclamoEntity entity = new ReclamoEntity();
        entity.setIdReclamo(dto.idReclamo());

        if (dto.clienteId() != null) {
            ClienteEntity clienteRef = new ClienteEntity();
            clienteRef.setIdCliente(dto.clienteId());
            entity.setCliente(clienteRef);
        }
        if (dto.empleadoId() != null) {
            EmpleadoEntity empleadoRef = new EmpleadoEntity();
            empleadoRef.setIdEmpleado(dto.empleadoId());
            entity.setEmpleado(empleadoRef);
        }
        entity.setFechaCreacion(dto.fechaCreacion() != null ? dto.fechaCreacion() : LocalDate.now());
        entity.setDescripcion(dto.descripcion());
        entity.setEstadoReclamo(dto.estadoReclamo() != null ? dto.estadoReclamo() : EstadoReclamo.PENDIENTE);
        entity.setRespuesta(dto.respuesta());
        return entity;
    }
    public String generarNumeroSeguimiento() {
        return "REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
