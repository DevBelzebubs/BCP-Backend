package com.backend.bcp.shared.Infraestructure.adapters.out;

import java.util.Optional;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;
import com.backend.bcp.shared.Domain.Usuario;
import com.backend.bcp.shared.Infraestructure.entity.UsuarioEntity;
import com.backend.bcp.shared.Infraestructure.repo.SpringDataUserRepository;
/*
public class JpaUserRepositoryAdapter implements UserRepository {
    private final SpringDataUserRepository repository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return repository.findByUsername(username).map(entity -> {
            if (entity instanceof ClienteEntity clienteEntity) {
                return new ClienteEntity(clienteEntity.getId(),clienteEntity.getNombre(),clienteEntity.getContrasena(),clienteEntity.getCorreo(),clienteEntity.getDni(),clienteEntity.getDireccion(),clienteEntity.getTelefono());
            }else if(entity instanceof EmpleadoEntity empleadoEntity){
                String rol =
        }});
    }
}
*/