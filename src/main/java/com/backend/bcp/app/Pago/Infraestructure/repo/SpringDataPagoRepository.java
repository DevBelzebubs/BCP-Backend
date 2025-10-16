package com.backend.bcp.app.Pago.Infraestructure.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;

@Repository
public interface SpringDataPagoRepository extends JpaRepository<PagoEntity,Long> {
    List<PagoEntity> findByEstado(String estado);
    List<PagoEntity> findPendientesByClienteId(Long usuarioId);
}