package com.backend.bcp.app.Prestamo.Infraestructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;

@Repository
public interface SpringDataPrestamoRepository extends JpaRepository<PrestamoEntity,Long> {

}   
