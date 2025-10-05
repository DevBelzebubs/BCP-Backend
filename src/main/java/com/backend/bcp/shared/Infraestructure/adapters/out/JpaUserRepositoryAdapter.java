package com.backend.bcp.shared.Infraestructure.adapters.out;

import java.util.Optional;

import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;
import com.backend.bcp.shared.Domain.Usuario;
/* 
public class JpaUserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository repository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return repository.findByUsername(username).map(user -> new Usuario(user.getUsername(), user.getPassword()));
    }

}
*/