package com.backend.bcp.shared.Aplication.Security.ports.out;

import java.util.Optional;

import com.backend.bcp.shared.Domain.Usuario;

public interface UserRepository {
    Optional<Usuario> findByUsername(String username);
}
