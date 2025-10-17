package com.backend.bcp.app.Usuario.Application.ports.out.Cliente;

import java.util.Optional;

import com.backend.bcp.app.Usuario.Domain.Cliente;

public interface ClienteRepository {
    Optional<Cliente> findById(Long id);
}
