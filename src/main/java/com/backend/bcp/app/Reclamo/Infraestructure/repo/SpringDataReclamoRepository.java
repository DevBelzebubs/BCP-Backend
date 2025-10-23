package com.backend.bcp.app.Reclamo.Infraestructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Reclamo.Infraestructure.entity.ReclamoEntity;

@Repository
public interface SpringDataReclamoRepository extends JpaRepository<ReclamoEntity,Long> {

}