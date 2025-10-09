package com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;

public interface SpringADataBackofficeRepository extends JpaRepository<BackOfficeEntity,Long> {
    Optional<BackOfficeEntity> findByIdEmpleado_IdEmpleado(Long idEmpleado);

}
