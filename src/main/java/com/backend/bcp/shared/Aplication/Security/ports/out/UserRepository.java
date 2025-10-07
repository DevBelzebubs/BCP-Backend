package com.backend.bcp.shared.Aplication.Security.ports.out;

import java.util.List;
import java.util.Optional;

import com.backend.bcp.shared.Domain.Usuario;

public interface UserRepository {
    List<Usuario> findAllUsuarios();
    Optional<Usuario> findByNombre(String nombre);
}
