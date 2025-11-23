package com.backend.bcp.app.shared.Application.dto.out;

import java.time.LocalDateTime;

import com.backend.bcp.app.shared.Infraestructure.entity.enums.EstadoAlerta;
import com.backend.bcp.app.shared.Infraestructure.entity.enums.TipoAlerta;

public record AlertaPersistenceDTO(
        Long id,
        LocalDateTime fechaHoraCreacion,
        TipoAlerta tipoAlerta,
        String mensaje,
        EstadoAlerta estado,
        Long usuarioNotificadoId 
) {
public AlertaPersistenceDTO(TipoAlerta tipoAlerta, String mensaje, Long usuarioNotificadoId) {
        this(null, LocalDateTime.now(), tipoAlerta, mensaje, EstadoAlerta.NUEVA, usuarioNotificadoId);
    }
}
