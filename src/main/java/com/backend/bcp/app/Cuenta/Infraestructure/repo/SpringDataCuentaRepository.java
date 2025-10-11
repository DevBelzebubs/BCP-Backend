package com.backend.bcp.app.Cuenta.Infraestructure.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;

@Repository
public interface SpringDataCuentaRepository extends JpaRepository<CuentaEntity,Long> {
    List<CuentaEntity> findByCliente_IdUsuario_Id(Long idUsuario);
}
