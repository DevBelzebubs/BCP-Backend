package com.backend.bcp.shared.Infraestructure.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;

public interface SpringADataBackofficeRepository extends JpaRepository<BackOfficeEntity,Long> {
    Optional<BackOfficeEntity> findByIdEmpleado_IdEmpleado(Long idEmpleado);

}
