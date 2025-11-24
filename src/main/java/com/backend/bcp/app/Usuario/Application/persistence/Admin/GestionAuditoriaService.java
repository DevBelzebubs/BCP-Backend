package com.backend.bcp.app.Usuario.Application.persistence.Admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.bcp.app.Usuario.Application.dto.in.RegistroAuditoriaDTO;
import com.backend.bcp.app.Usuario.Application.dto.out.AuditoriaRolDTO;
import com.backend.bcp.app.Usuario.Application.mapper.AuditoriaPersistence;
import com.backend.bcp.app.Usuario.Application.ports.in.Admin.GestionAuditoriaUseCase;
import com.backend.bcp.app.shared.Infraestructure.entity.AuditoriaRolEntity;
import com.backend.bcp.app.shared.Infraestructure.entity.RegistroAuditoriaFinanciera;
import com.backend.bcp.app.shared.Infraestructure.repo.SpringDataAuditoriaRolRepository;
import com.backend.bcp.app.shared.Infraestructure.repo.SpringDataRegistroAuditoriaFinancieraRepository;

@Service
public class GestionAuditoriaService implements GestionAuditoriaUseCase {
    private final SpringDataAuditoriaRolRepository auditoriaRolRepository;
    private final SpringDataRegistroAuditoriaFinancieraRepository auditoriaFinancieraRepository;
    private final AuditoriaPersistence auditoriaPersistence;

    public GestionAuditoriaService(SpringDataAuditoriaRolRepository auditoriaRolRepository,
            SpringDataRegistroAuditoriaFinancieraRepository auditoriaFinancieraRepository,
            AuditoriaPersistence auditoriaPersistence) {
        this.auditoriaRolRepository = auditoriaRolRepository;
        this.auditoriaFinancieraRepository = auditoriaFinancieraRepository;
        this.auditoriaPersistence = auditoriaPersistence;
    }

    @Override
    public List<AuditoriaRolDTO> listarAuditoriasRol() {
        List<AuditoriaRolEntity> auditorias = auditoriaRolRepository
                .findAll(Sort.by(Sort.Direction.DESC, "fechaHoraCambio"));
        return auditorias.stream()
                .map(auditoriaPersistence::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistroAuditoriaDTO> listarAuditoriasFinancieras() {
        List<RegistroAuditoriaFinanciera> auditorias = auditoriaFinancieraRepository
                .findAll(Sort.by(Sort.Direction.DESC, "fechaHora"));
        return auditorias.stream()
                .map(auditoriaPersistence::mapToFinancieraDTO)
                .collect(Collectors.toList());
    }

}
