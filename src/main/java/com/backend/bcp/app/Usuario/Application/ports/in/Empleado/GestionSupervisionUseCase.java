package com.backend.bcp.app.Usuario.Application.ports.in.Empleado;

import java.util.List;

import com.backend.bcp.app.Usuario.Application.dto.in.MarcarSospechosoRequestDTO;
import com.backend.bcp.app.Usuario.Application.dto.out.OperacionSupervisionDTO;

public interface GestionSupervisionUseCase {
    List<OperacionSupervisionDTO> obtenerOperacionesParaSupervisar();
    Long marcarOperacionComoSospechosa(MarcarSospechosoRequestDTO request, Long backOfficeUserId);
    byte[] generarReporteGlobalOperaciones();
}
