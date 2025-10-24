package com.backend.bcp.app.Shared.Infraestructure.adapters.out;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Shared.Infraestructure.entity.AuditoriaRolEntity;
import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;
import com.backend.bcp.app.Shared.Infraestructure.repo.SpringDataAuditoriaRolRepository;
import com.backend.bcp.app.Usuario.Application.ports.out.Admin.AuditoriaRolPort;
@Component
public class JpaAuditoriaRolAdapter implements AuditoriaRolPort {

    private final SpringDataAuditoriaRolRepository repository;

    public JpaAuditoriaRolAdapter(SpringDataAuditoriaRolRepository repository) {
        this.repository = repository;
    }
    @Override
    public void guardarAuditoria(Long adminId, Long empleadoId, TipoRol rolAnterior, TipoRol rolNuevo,
            LocalDateTime fechaHora) {
        AuditoriaRolEntity logEntry = new AuditoriaRolEntity();
        logEntry.setAdminId(adminId);
        logEntry.setEmpleadoId(empleadoId);
        logEntry.setRolAnterior(rolAnterior);
        logEntry.setRolNuevo(rolNuevo);
        logEntry.setFechaHoraCambio(fechaHora);
        repository.save(logEntry);
    }

}
