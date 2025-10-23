package com.backend.bcp.app.Pago.Infraestructure.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;

@Repository
public interface SpringDataPagoRepository extends JpaRepository<PagoEntity,Long> {
    List<PagoEntity> findByEstado(String estado);
    @Query("SELECT p FROM PagoEntity p " +
           "WHERE p.estado = 'PENDIENTE' AND p.cliente.idUsuario.id = :usuarioId")
    List<PagoEntity> findPendientesByClienteId(Long usuarioId);
    List<PagoEntity> findByFecha(LocalDate fecha);
}