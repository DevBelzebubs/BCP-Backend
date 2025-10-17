package com.backend.bcp.app.shared.Application.Security.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.shared.Application.Security.dto.in.UsuarioDTO;

public interface UserRepository {
    List<UsuarioDTO> findAllUsuarios();
    Optional<UsuarioDTO> findByNombre(String nombre);
}
