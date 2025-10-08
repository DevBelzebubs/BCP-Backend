package com.backend.bcp.shared.Infraestructure.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;

public interface SpringDataEmpleadoRepository extends JpaRepository<EmpleadoEntity,Long> {
    Optional<EmpleadoEntity> findByIdUsuario_Id(Long idUsuario);

}
