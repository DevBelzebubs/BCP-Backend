package com.backend.bcp.app.Shared.Application.Security.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;

public interface UserRepository {
    List<UsuarioDTO> findAllUsuarios();
    Optional<UsuarioDTO> findByNombre(String nombre);
    Optional<UsuarioDTO> findById(Long id);
    Optional<UsuarioDTO> findByDni(String dni);
}
