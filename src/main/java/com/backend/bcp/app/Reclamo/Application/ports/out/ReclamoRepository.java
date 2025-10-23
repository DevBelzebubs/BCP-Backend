package com.backend.bcp.app.Reclamo.Application.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoPersistenceDTO;

public interface ReclamoRepository {
    Optional<ReclamoPersistenceDTO> findById(Long id);
    ReclamoPersistenceDTO guardarReclamo(ReclamoPersistenceDTO reclamo);
    List<ReclamoPersistenceDTO> findAll();
}
