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

@Component
public class PagoPersistenceMapper {
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
            PrestamoEntity prestamoRef = new PrestamoEntity();
            // Asumo que Prestamo tiene getId()
            prestamoRef.setIdPrestamo(pagoPrestamo.getPrestamo().getId()); 
            entity.setPrestamo(prestamoRef);
            
            // Seteo de campos comunes (asumiendo setters en PagoEntity)
            entity.setMonto(pagoPrestamo.getMonto());
            entity.setFecha(pagoPrestamo.getFecha());
            entity.setEstado("PAGADO"); 

            return entity;
        }else if (domain instanceof PagoServicio pagoServicio) {
            PagoServicioEntity entity = new PagoServicioEntity();
            
            // Seteo de FK
            ServicioEntity servicioRef = new ServicioEntity();
            // Asumo que Servicio tiene getId()
            // servicioRef.setIdServicio(pagoServicio.getServicio().getId()); 
            entity.setServicio(servicioRef);
            
            // Seteo de campos comunes
            entity.setMonto(pagoServicio.getMonto());
            entity.setFecha(pagoServicio.getFecha());
            entity.setEstado("PAGADO"); 
            
            return entity;
        }
        throw new IllegalArgumentException("Tipo de Pago de Dominio no reconocido: " + domain.getClass().getSimpleName());
    }
}