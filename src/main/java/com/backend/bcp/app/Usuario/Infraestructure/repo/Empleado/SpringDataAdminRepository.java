package com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AdminEntity;

@Repository
public interface SpringDataAdminRepository extends JpaRepository<AdminEntity,Long> {
        Optional<AdminEntity> findByIdEmpleado_IdEmpleado(Long idEmpleado);
}