package com.backend.bcp.app.Usuario.Application.ports.out.Empleado;

import java.time.LocalDate;
import java.util.List;

import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.dto.out.MovimientoPersistenceDTO;

public interface ReporteDiarioDataPort {
    List<MovimientoPersistenceDTO> findTransaccionesByFecha(LocalDate fecha);
    List<PagoPersistenceDTO> findPagosByFecha(LocalDate fecha);
    List<PrestamoPersistenceDTO> findPrestamosNuevosByFecha(LocalDate fecha);
}
