package com.backend.bcp.app.Usuario.Application.ports.in.Empleado;

import java.time.LocalDate;

public interface GenerarReporteDiarioUseCase {
    byte[] generarReporte(LocalDate fecha);
}
