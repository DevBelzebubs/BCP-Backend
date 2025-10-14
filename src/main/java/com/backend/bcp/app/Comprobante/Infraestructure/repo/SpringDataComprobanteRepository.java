package com.backend.bcp.app.Comprobante.Infraestructure.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Infraestructure.entity.ComprobanteEntity;

@Service
public interface SpringDataComprobanteRepository extends JpaRepository<ComprobanteEntity,Long> {

}
