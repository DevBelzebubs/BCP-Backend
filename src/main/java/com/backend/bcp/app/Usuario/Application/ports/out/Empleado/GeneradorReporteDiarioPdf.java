package com.backend.bcp.app.Usuario.Application.ports.out.Empleado;

import java.time.LocalDate;
import java.util.List;

import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;

public interface GeneradorReporteDiarioPdf {
    byte[] generarPdf(LocalDate fecha, 
                      List<MovimientoPersistenceDTO> transacciones,
                      List<PagoPersistenceDTO> pagos,
                      List<PrestamoPersistenceDTO> prestamos);
}
