package com.backend.bcp.app.Usuario.Application.ports.in.Empleado;

import com.backend.bcp.app.Usuario.Application.dto.in.ConciliacionRequestDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.ConciliacionResultDTO;

public interface GestionarConciliacionUseCase {
    ConciliacionResultDTO procesarConciliacion(ConciliacionRequestDTO request, Long backOfficeUserId);

}
