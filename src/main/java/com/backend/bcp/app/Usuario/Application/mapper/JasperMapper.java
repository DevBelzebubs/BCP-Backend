package com.backend.bcp.app.Usuario.Application.mapper;


import org.springframework.stereotype.Component;

import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Pago.Domain.PagoPrestamo;
import com.backend.bcp.app.Pago.Domain.PagoServicio;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;

@Component
public class JasperMapper {
    public Transaccion toTransaccionDomain(MovimientoPersistenceDTO dto){
        return new Transaccion(dto.id(), null, dto.tipo(), dto.monto(), dto.fecha());
    }
    public Pago toPagoDomain(PagoPersistenceDTO dto){
        if ("PRESTAMO".equals(dto.tipoPago())) {
            Prestamo prestamoRef = new Prestamo(dto.prestamoId(), null, null, 0, 1, null);
            return new PagoPrestamo(dto.id(), dto.monto(), dto.fecha(), prestamoRef, dto.estado(), null);
        } else if ("SERVICIO".equals(dto.tipoPago())){
            Servicio servicioRef = new Servicio(dto.servicioId(), "Servicio ID: " + dto.servicioId(), null, null);
            return new PagoServicio(dto.id(), dto.monto(), dto.fecha(), servicioRef, dto.estado(), null);
        }
        return null;
    }
}
