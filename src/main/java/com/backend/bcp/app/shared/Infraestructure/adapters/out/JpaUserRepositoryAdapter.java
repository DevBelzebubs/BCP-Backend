package com.backend.bcp.app.Shared.Infraestructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.mapper.UsuarioMapper;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Shared.Infraestructure.repo.SpringDataUserRepository;

@Component
public class JpaUserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository repository;
    private final UsuarioMapper mapper;
    public JpaUserRepositoryAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
        this.mapper = new UsuarioMapper();
    }
    @Override
    public List<UsuarioDTO> findAllUsuarios() {
        return repository.findAll().stream().map(mapper::toPersistenceDTO).collect(Collectors.toList());
    }
    @Override
    public Optional<UsuarioDTO> findByNombre(String nombre) {
        return repository.findByNombre(nombre).map(mapper::toPersistenceDTO);
    }
    @Override
    public Optional<UsuarioDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toPersistenceDTO);
    }   
}