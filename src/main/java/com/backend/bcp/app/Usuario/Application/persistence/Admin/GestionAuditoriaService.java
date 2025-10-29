package com.backend.bcp.app.Usuario.Application.persistence.Admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.bcp.app.Shared.Infraestructure.entity.AuditoriaRolEntity;
import com.backend.bcp.app.Shared.Infraestructure.repo.SpringDataAuditoriaRolRepository;
import com.backend.bcp.app.Usuario.Application.dto.out.AuditoriaRolDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Admin.GestionAuditoriaUseCase;

@Service
public class GestionAuditoriaService implements GestionAuditoriaUseCase {
    private final SpringDataAuditoriaRolRepository auditoriaRolRepository;

    public GestionAuditoriaService(SpringDataAuditoriaRolRepository auditoriaRolRepository) {
        this.auditoriaRolRepository = auditoriaRolRepository;
    }
    @Override
    public List<AuditoriaRolDTO> listarAuditoriasRol() {
        List<AuditoriaRolEntity> auditorias = auditoriaRolRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaHoraCambio"));
        
        return auditorias.stream()
                         .map(this::mapToDTO)
                         .collect(Collectors.toList());
    }
    private AuditoriaRolDTO mapToDTO(AuditoriaRolEntity entity) {
        return new AuditoriaRolDTO(
            entity.getId(),
            entity.getFechaHoraCambio(),
            entity.getAdminId(),
            entity.getEmpleadoId(),
            entity.getRolAnterior(),
            entity.getRolNuevo()
        );
    }

}
