package com.backend.bcp.app.Servicio.Infraestructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;

public interface SpringDataServicioRepository extends JpaRepository<ServicioEntity,Long> {

}
