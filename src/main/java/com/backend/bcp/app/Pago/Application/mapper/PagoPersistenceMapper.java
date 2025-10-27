package com.backend.bcp.app.Pago.Application.mapper;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Application.dto.out.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Pago.Domain.PagoPrestamo;
import com.backend.bcp.app.Pago.Domain.PagoServicio;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoPrestamoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;
import com.backend.bcp.app.Prestamo.Infraestructure.repo.SpringDataPrestamoRepository;
import com.backend.bcp.app.Servicio.Application.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;
import com.backend.bcp.app.Servicio.Infraestructure.repo.SpringDataServicioRepository;
import com.backend.bcp.app.Usuario.Domain.Cliente;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

@Component
public class PagoPersistenceMapper {
    private final SpringDataServicioRepository servicioRepository;
    private final SpringDataPrestamoRepository prestamoRepository;

    public PagoPersistenceMapper(SpringDataServicioRepository servicioRepository,
            SpringDataPrestamoRepository prestamoRepository) {
        this.servicioRepository = servicioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    private ClienteEntity createClienteReference(Long clienteId) {
        if (clienteId == null)
            return null;
        ClienteEntity clienteRef = new ClienteEntity();
        clienteRef.setIdCliente(clienteId);
        return clienteRef;
    }

    public PagoPersistenceDTO toPersistenceDTO(PagoEntity entity) {
        if (entity == null)
            return null;

        Long prestamoId = null;
        Long servicioId = null;
        String tipoPago = "DESCONOCIDO";

        if (entity instanceof PagoPrestamoEntity pagoPrestamo) {
            if (pagoPrestamo.getPrestamo() != null) {
                prestamoId = pagoPrestamo.getPrestamo().getIdPrestamo();
            }
            tipoPago = "PRESTAMO";
        } else if (entity instanceof PagoServicioEntity pagoServicio) {
            if (pagoServicio.getServicio() != null) {
                servicioId = pagoServicio.getServicio().getIdServicio();
            }
            tipoPago = "SERVICIO";
        }

        return new PagoPersistenceDTO(
                entity.getIdPago(),
                prestamoId,
                servicioId,
                entity.getMonto(),
                entity.getFecha(),
                entity.getEstado(),
                tipoPago);
    }

    public PagoEntity toEntity(Pago domain) {
        if (domain == null)
            return null;

        if (domain instanceof PagoPrestamo pagoPrestamo) {
            PagoPrestamoEntity entity = new PagoPrestamoEntity();

            Long clienteId = pagoPrestamo.getClienteId();
            entity.setCliente(createClienteReference(clienteId));

            PrestamoEntity prestamoRef = new PrestamoEntity();
            prestamoRef.setIdPrestamo(pagoPrestamo.getPrestamo().getId());
            entity.setPrestamo(prestamoRef);

            entity.setMonto(pagoPrestamo.getMonto());
            entity.setFecha(pagoPrestamo.getFecha());
            entity.setEstado(pagoPrestamo.getEstado());

            return entity;

        } else if (domain instanceof PagoServicio pagoServicio) {
            PagoServicioEntity entity = new PagoServicioEntity();

            Long clienteId = pagoServicio.getClienteId();
            entity.setCliente(createClienteReference(clienteId));

            ServicioEntity servicioRef = new ServicioEntity();
            servicioRef.setIdServicio(pagoServicio.getServicio().getIdServicio());
            entity.setServicio(servicioRef);

            entity.setMonto(pagoServicio.getMonto());
            entity.setFecha(pagoServicio.getFecha());
            entity.setEstado(pagoServicio.getEstado());

            return entity;
        }
        throw new IllegalArgumentException(
                "Tipo de Pago de Dominio no reconocido: " + domain.getClass().getSimpleName());
    }
    public Cuenta toCuentaDomain(CuentaPersistenceDTO dto) {
        if (dto == null) throw new RuntimeException("Cuenta no encontrada.");
        Cliente cliente = new Cliente();
        if (dto.cliente() != null) cliente.setId(dto.cliente().id());
        
        return new Cuenta(dto.id(), cliente, dto.tipo(), dto.estadoCuenta(), dto.numeroCuenta(), dto.saldo());
    }
    public Servicio toServicioDomain(ServicioPersistenceDTO dto) {
        return new Servicio(dto.id(), dto.nombre(), dto.descripcion(), dto.recibo()); 
    }
    public PagoPendienteDTO toPagoPendienteDTO(PagoPersistenceDTO dto) {
        String nombreItem = "Desconocido";

        if (dto.servicioId() != null) {
            nombreItem = servicioRepository.findById(dto.servicioId())
                    .map(ServicioEntity::getNombre)
                    .orElse("Servicio ID: " + dto.servicioId());

        } else if (dto.prestamoId() != null) {
            nombreItem = prestamoRepository.findById(dto.prestamoId())
                    .map(prestamo -> "Préstamo de S/ " + prestamo.getMonto().toString())
                    .orElse("Préstamo ID: " + dto.prestamoId());
        }

        return new PagoPendienteDTO(
                dto.id(),
                nombreItem,
                dto.monto());
    }
}