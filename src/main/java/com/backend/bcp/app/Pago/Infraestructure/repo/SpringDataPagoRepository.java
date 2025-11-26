package com.backend.bcp.app.Pago.Infraestructure.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Pago.Infraestructure.entity.PagoEntity;

@Repository
public interface SpringDataPagoRepository extends JpaRepository<PagoEntity,Long> {
    List<PagoEntity> findByEstado(String estado);
    @Query(value = "SELECT p.* FROM Pago p " +
                   "INNER JOIN cliente c ON p.ID_Cliente = c.ID_Cliente " +
                   "INNER JOIN usuario u ON c.Usuario_ID_Usuario = u.ID_Usuario " +
                   "WHERE p.Estado = 'PENDIENTE' AND u.ID_Usuario = :usuarioId", 
           nativeQuery = true)
    List<PagoEntity> findPendientesByClienteId(@Param("usuarioId") Long usuarioId);
    List<PagoEntity> findByFecha(LocalDate fecha);
}