package com.backend.bcp.app.Pago.Aplication.dto.out;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Pago.Aplication.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Pago.Domain.PagoPrestamo;
import com.backend.bcp.app.Pago.Domain.PagoServicio;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoPrestamoEntity;
import com.backend.bcp.app.Pago.Infraestructure.entity.PagoServicioEntity;
import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;
import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

@Component
public class PagoPersistenceMapper {
     private ClienteEntity createClienteReference(Long clienteId) {
        if (clienteId == null) return null;
        ClienteEntity clienteRef = new ClienteEntity();
        clienteRef.setIdCliente(clienteId);
        return clienteRef;
    }
    public PagoPersistenceDTO toPersistenceDTO(PagoEntity entity){
        if (entity == null) return null;

        Long prestamoId = null;
        Long servicioId = null;
        String tipoPago = "DESCONOCIDO";

        if (entity instanceof PagoPrestamoEntity pagoPrestamo) {
            if (pagoPrestamo.getPrestamo() != null) {
                prestamoId = pagoPrestamo.getPrestamo().getIdPrestamo();
            }
            tipoPago = "PRESTAMO";
        }else if (entity instanceof PagoServicioEntity pagoServicio) {
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
            tipoPago
        );
    }
    public PagoEntity toEntity(Pago domain){
        if (domain == null) return null;

        if (domain instanceof PagoPrestamo pagoPrestamo) {
            PagoPrestamoEntity entity = new PagoPrestamoEntity();
            
            // 1. Obtener ID de Cliente desde el campo expuesto en la clase Pago (asumido)
            Long clienteId = pagoPrestamo.getClienteId(); 
            entity.setCliente(createClienteReference(clienteId)); // AJUSTE CLAVE: Setea la FK del cliente en la base
            
            // 2. Seteo de FK específica de Prestamo
            PrestamoEntity prestamoRef = new PrestamoEntity();
            prestamoRef.setIdPrestamo(pagoPrestamo.getPrestamo().getId()); 
            entity.setPrestamo(prestamoRef);
            
            // 3. Seteo de campos comunes 
            entity.setMonto(pagoPrestamo.getMonto());
            entity.setFecha(pagoPrestamo.getFecha());
            entity.setEstado(pagoPrestamo.getEstado()); 

            return entity;
            
        }else if (domain instanceof PagoServicio pagoServicio) {
            PagoServicioEntity entity = new PagoServicioEntity();
            
            // 1. Obtener ID de Cliente desde el campo expuesto en la clase Pago (asumido)
            Long clienteId = pagoServicio.getClienteId(); 
            entity.setCliente(createClienteReference(clienteId)); // AJUSTE CLAVE: Setea la FK del cliente en la base
            
            // 2. Seteo de FK específica de Servicio
            ServicioEntity servicioRef = new ServicioEntity();
            servicioRef.setIdServicio(pagoServicio.getServicio().getIdServicio()); 
            entity.setServicio(servicioRef);
            
            // 3. Seteo de campos comunes
            entity.setMonto(pagoServicio.getMonto());
            entity.setFecha(pagoServicio.getFecha());
            entity.setEstado(pagoServicio.getEstado()); 
            
            return entity;
        }
        throw new IllegalArgumentException("Tipo de Pago de Dominio no reconocido: " + domain.getClass().getSimpleName());
    }
}