package com.backend.bcp.app.Usuario.Application.ports.out.Empleado;

import com.backend.bcp.app.shared.Application.dto.out.AlertaPersistenceDTO;

public interface AlertaRepositoryPort {
    AlertaPersistenceDTO guardarAlerta(AlertaPersistenceDTO alertaDto);
}
