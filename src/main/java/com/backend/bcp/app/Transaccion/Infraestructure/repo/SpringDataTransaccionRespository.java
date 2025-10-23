package com.backend.bcp.app.Transaccion.Infraestructure.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.bcp.app.Transaccion.Infraestructure.entity.TransaccionEntity;

public interface SpringDataTransaccionRespository extends JpaRepository<TransaccionEntity,Long> {
    List<TransaccionEntity> findTop10ByCuenta_IdCuentaOrderByFechaDesc(Long cuentaId);
    List<TransaccionEntity> findByFechaBetween(LocalDateTime inicioDelDia, LocalDateTime finDelDia);
}