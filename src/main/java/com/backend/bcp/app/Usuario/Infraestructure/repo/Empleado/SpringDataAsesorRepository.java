package com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;

@Repository
public interface SpringDataAsesorRepository extends JpaRepository<AsesorEntity,Long>{
    Optional<AsesorEntity> findByIdEmpleado_IdEmpleado(Long idEmpleado);

}
