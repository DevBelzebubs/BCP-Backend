package com.backend.bcp.app.Reclamo.Application.out;

import java.util.List;

import com.backend.bcp.app.Reclamo.Domain.Reclamo;

public interface ReclamoService {
    Reclamo findById(Long id);
    void guardarReclamo(Reclamo reclamo);
    List<Reclamo> findAll();
}
