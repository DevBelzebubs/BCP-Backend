package com.backend.bcp.app.Shared.Infraestructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bcp.app.Shared.Infraestructure.entity.RegistroAuditoriaFinanciera;

public interface SpringDataRegistroAuditoriaFinancieraRepository extends JpaRepository<RegistroAuditoriaFinanciera, Long> {
    
}
