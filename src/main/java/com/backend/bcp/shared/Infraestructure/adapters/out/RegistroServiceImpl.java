package com.backend.bcp.shared.Infraestructure.adapters.out;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;
import com.backend.bcp.shared.Aplication.Security.dto.in.ClienteDTO;
import com.backend.bcp.shared.Aplication.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.shared.Aplication.Security.ports.in.RegistroService;
import com.backend.bcp.shared.Infraestructure.entity.UsuarioEntity;
import com.backend.bcp.shared.Infraestructure.repo.SpringDataUserRepository;

@Service
public class RegistroServiceImpl implements RegistroService {
    private final SpringDataUserRepository springDataUserRepository;
    private final SpringDataClientRepository springDataClientRepository;
    private final SpringDataEmpleadoRepository springDataEmpleadoRepository;
    public RegistroServiceImpl(SpringDataUserRepository springDataUserRepository,
            SpringDataClientRepository springDataClientRepository,
            SpringDataEmpleadoRepository springDataEmpleadoRepository) {
        this.springDataUserRepository = springDataUserRepository;
        this.springDataClientRepository = springDataClientRepository;
        this.springDataEmpleadoRepository = springDataEmpleadoRepository;
    }

    @Override
    public ClienteEntity registrarCliente(ClienteDTO dto) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(dto.getContrasena());
        usuario.setCorreo(dto.getCorreo());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());
        springDataUserRepository.save(usuario);

        ClienteEntity cliente = new ClienteEntity();
        cliente.setFechaRegistro(LocalDate.now());
        cliente.setIdUsuario(usuario);

        return springDataClientRepository.save(cliente);
    }

    @Override
    public EmpleadoEntity registrarEmpleado(EmpleadoDTO dto) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(dto.getContrasena());
        usuario.setCorreo(dto.getCorreo());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());

        springDataUserRepository.save(usuario);

        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setFechaContratacion(LocalDate.now());
        empleado.setSalario(dto.getSalario());
        empleado.setIdUsuario(usuario);

        return springDataEmpleadoRepository.save(empleado);
    }

}
