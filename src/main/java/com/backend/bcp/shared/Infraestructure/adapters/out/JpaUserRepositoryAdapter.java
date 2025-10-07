package com.backend.bcp.shared.Infraestructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.bcp.shared.Aplication.Security.dto.out.UsuarioMapper;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;
import com.backend.bcp.shared.Domain.Usuario;
import com.backend.bcp.shared.Infraestructure.repo.SpringDataUserRepository;

@Component
public class JpaUserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository repository;
    private final UsuarioMapper mapper;
    public JpaUserRepositoryAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
        this.mapper = new UsuarioMapper();
    }
    @Override
    public List<Usuario> findAllUsuarios() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
    @Override
    public Optional<Usuario> findByNombre(String nombre) {
        return repository.findByNombre(nombre).map(mapper::toDomain);
    }   
}