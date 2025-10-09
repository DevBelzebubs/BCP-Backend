package com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

@Repository
public interface SpringDataClientRepository extends JpaRepository<ClienteEntity,Long> {
    Optional<ClienteEntity> findByIdUsuario_Id(Long idUsuario);

}
