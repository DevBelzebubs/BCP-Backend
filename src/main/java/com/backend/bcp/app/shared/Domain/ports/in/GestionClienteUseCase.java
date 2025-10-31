package com.backend.bcp.app.Shared.Domain.ports.in;

import com.backend.bcp.app.Shared.Application.dto.in.LoadClientDataDTO;

public interface GestionClienteUseCase {
    LoadClientDataDTO cargarDatosDashboard(String dni);
}
