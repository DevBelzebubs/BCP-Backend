package com.backend.bcp.app.shared.Infraestructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.shared.Infraestructure.entity.AlertaEntity;

@Repository
public interface SpringDataAlertaRepository extends JpaRepository<AlertaEntity, Long> {

}
