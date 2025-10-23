package com.backend.bcp.app.Usuario.Application.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Pago.Application.dto.in.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Application.mapper.PrestamoMapper;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Transaccion.Application.dto.in.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Usuario.Application.mapper.JasperMapper;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GenerarReporteDiarioUseCase;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.GeneradorReporteDiarioPdf;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.ReporteDiarioDataPort;

@Service
public class ReporteDiarioService implements GenerarReporteDiarioUseCase{
    private final ReporteDiarioDataPort reporteDiarioDataPort;
    private final GeneradorReporteDiarioPdf generadorReporteDiarioPdf;
    private final JasperMapper jasperMapper;
    
    public ReporteDiarioService(ReporteDiarioDataPort reporteDiarioDataPort,
            GeneradorReporteDiarioPdf generadorReporteDiarioPdf, PrestamoMapper prestamoMapper,
            JasperMapper jasperMapper) {
        this.reporteDiarioDataPort = reporteDiarioDataPort;
        this.generadorReporteDiarioPdf = generadorReporteDiarioPdf;
        this.jasperMapper = jasperMapper;
    }

    @Override
    public byte[] generarReporte(LocalDate fecha) {
        List<MovimientoPersistenceDTO> transaccionDTOs = reporteDiarioDataPort.findTransaccionesByFecha(fecha);
        List<PagoPersistenceDTO> pagoDTOs = reporteDiarioDataPort.findPagosByFecha(fecha);
        List<PrestamoPersistenceDTO> prestamoDTOs = reporteDiarioDataPort.findPrestamosNuevosByFecha(fecha);

        return generadorReporteDiarioPdf.generarPdf(fecha, transaccionDTOs, pagoDTOs, prestamoDTOs);
    }

}
