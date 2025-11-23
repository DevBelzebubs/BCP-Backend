package com.backend.bcp.app.shared.Domain.ports.in;

import com.backend.bcp.app.shared.Application.dto.in.LoadClientDataDTO;

public interface GestionClienteUseCase {
    LoadClientDataDTO cargarDatosDashboard(String dni);
}
