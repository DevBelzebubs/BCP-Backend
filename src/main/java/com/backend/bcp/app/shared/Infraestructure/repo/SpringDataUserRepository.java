package com.backend.bcp.app.shared.Infraestructure.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.shared.Infraestructure.entity.UsuarioEntity;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UsuarioEntity,Long> {
       Optional<UsuarioEntity> findByNombre(String nombre);
}
