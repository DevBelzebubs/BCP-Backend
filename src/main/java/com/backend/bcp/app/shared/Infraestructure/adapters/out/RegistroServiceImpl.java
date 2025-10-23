package com.backend.bcp.app.Shared.Infraestructure.adapters.out;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.bcp.app.Shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.in.RegistroService;
import com.backend.bcp.app.Shared.Infraestructure.entity.UsuarioEntity;
import com.backend.bcp.app.Shared.Infraestructure.repo.SpringDataUserRepository;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistroServiceImpl implements RegistroService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final SpringDataUserRepository springDataUserRepository;
    private final SpringDataClientRepository springDataClientRepository;
    private final SpringDataEmpleadoRepository springDataEmpleadoRepository;
    private final SpringDataAsesorRepository springDataAsesorRepository;
    private final SpringADataBackofficeRepository springADataBackofficeRepository;
    public RegistroServiceImpl(SpringDataUserRepository springDataUserRepository,
            SpringDataClientRepository springDataClientRepository,
            SpringDataEmpleadoRepository springDataEmpleadoRepository, SpringDataAsesorRepository springDataAsesorRepository, SpringADataBackofficeRepository springADataBackofficeRepository) {
        this.springDataUserRepository = springDataUserRepository;
        this.springDataClientRepository = springDataClientRepository; 
        this.springDataEmpleadoRepository = springDataEmpleadoRepository;
        this.springDataAsesorRepository = springDataAsesorRepository;
        this.springADataBackofficeRepository = springADataBackofficeRepository;
    }

    @Override
    public ClienteEntity registrarCliente(ClienteDTO dto) {
        String passwordEncrypt = passwordEncoder.encode(dto.getContrasena());
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(passwordEncrypt);
        usuario.setCorreo(dto.getCorreo());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());

        ClienteEntity cliente = new ClienteEntity();
        cliente.setFechaRegistro(LocalDate.now());
        cliente.setIdUsuario(usuario);

        return springDataClientRepository.save(cliente);
    }
    public EmpleadoEntity crearEmpleado(EmpleadoDTO dto){
        String passwordEncrypt = passwordEncoder.encode(dto.getContrasena());
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(passwordEncrypt);
        usuario.setCorreo(dto.getCorreo());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());


        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setFechaContratacion(LocalDate.now());
        empleado.setSalario(dto.getSalario());
        empleado.setIdUsuario(usuario);
        return empleado;
    }
    @Override
    public EmpleadoEntity registrarEmpleado(EmpleadoDTO dto) {
        EmpleadoEntity empleadoBase = crearEmpleado(dto);
        return springDataEmpleadoRepository.save(empleadoBase);
    }

    @Override
    @Transactional
    public AsesorEntity registrarAsesor(EmpleadoDTO dto) {
        EmpleadoEntity empleadoBase = crearEmpleado(dto);
        AsesorEntity asesor = new AsesorEntity();
        asesor.setIdEmpleado(empleadoBase);
        return springDataAsesorRepository.save(asesor);
    }

    @Override
    public BackOfficeEntity registrarBackOffice(EmpleadoDTO dto) {
        EmpleadoEntity empleadoBase = crearEmpleado(dto);
        BackOfficeEntity backOffice = new BackOfficeEntity();
        backOffice.setIdEmpleado(empleadoBase);
        return springADataBackofficeRepository.save(backOffice);
    }

}
