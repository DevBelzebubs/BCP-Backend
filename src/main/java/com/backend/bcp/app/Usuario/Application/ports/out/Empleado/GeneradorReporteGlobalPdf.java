package com.backend.bcp.app.Usuario.Application.ports.out.Empleado;

import java.util.List;

import com.backend.bcp.app.Usuario.Application.dto.out.OperacionSupervisionDTO;

public interface GeneradorReporteGlobalPdf {
    byte[] generarPdf(List<OperacionSupervisionDTO> operaciones);
}
