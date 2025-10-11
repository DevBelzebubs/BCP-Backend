package com.backend.bcp.shared.Aplication.Security.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.shared.Aplication.Security.dto.in.UsuarioDTO;

public interface UserRepository {
    List<UsuarioDTO> findAllUsuarios();
    Optional<UsuarioDTO> findByNombre(String nombre);
}
